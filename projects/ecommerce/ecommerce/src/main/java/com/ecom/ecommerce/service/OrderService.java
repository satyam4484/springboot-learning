package com.ecom.ecommerce.service;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.dto.CreateOrderRequest;
import com.ecom.ecommerce.dto.OrderDto;
import com.ecom.ecommerce.dto.OrderItemDto;
import com.ecom.ecommerce.dto.OrderItemRequest;
import com.ecom.ecommerce.entity.Address;
import com.ecom.ecommerce.entity.Order;
import com.ecom.ecommerce.entity.OrderItem;
import com.ecom.ecommerce.entity.Product;
import com.ecom.ecommerce.entity.User;
import com.ecom.ecommerce.exception.ApplicationException;
import com.ecom.ecommerce.repository.AddressRepository;
import com.ecom.ecommerce.repository.OrderRepository;
import com.ecom.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductCacheEvictionService productCacheEvictionService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final UserContextService userContextService;
    private final ModelMapper modelMapper;

    @Transactional
    public OrderDto createOrder(
            CreateOrderRequest request,
            Authentication authentication) {

        // 1. Get logged-in user from JWT
        User user = userContextService.getLoggedInUser(authentication);

        // 2. Find selected address
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() ->
                        new ApplicationException(
                                "Address not found with id: "
                                        + request.getAddressId(),
                                ErrorCode.ADDRESS_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        )
                );

        // 3. Make sure address belongs to logged-in user
        if (!address.getUser().getId().equals(user.getId())) {
            throw new ApplicationException(
                    "You are not authorized to use this address",
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN
            );
        }

        // 4. Create shipping address snapshot
        String shippingAddress =
                address.getAddressLine()
                        + ", "
                        + address.getCity()
                        + ", "
                        + address.getState()
                        + " - "
                        + address.getPincode()
                        + ", Contact: "
                        + address.getContactNo();

        // 5. Create order
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 6. Process each order item
        for (OrderItemRequest itemRequest : request.getItems()) {

            // 6.1 Find product
            Product product = productRepository.findById(
                            itemRequest.getProductId()
                    )
                    .orElseThrow(() ->
                            new ApplicationException(
                                    "Product not found with id: "
                                            + itemRequest.getProductId(),
                                    ErrorCode.PRODUCT_NOT_FOUND,
                                    HttpStatus.NOT_FOUND
                            )
                    );

            // 6.2 Check stock
            if (product.getStockQuantity()
                    < itemRequest.getQuantity()) {

                throw new ApplicationException(
                        "Insufficient stock for product: "
                                + product.getName(),
                        ErrorCode.INVALID_REQUEST,
                        HttpStatus.BAD_REQUEST
                );
            }

            // 6.3 Calculate item total
            BigDecimal itemTotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            itemRequest.getQuantity()
                                    )
                            );

            // 6.4 Create order item
            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());

            // Store price at purchase time
            orderItem.setPrice(product.getPrice());

            // Add item to order
            order.getOrderItems().add(orderItem);

            // Add item price to order total
            totalAmount = totalAmount.add(itemTotal);

            // 6.5 Reduce product stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            - itemRequest.getQuantity()
            );

            // invalidate product cache after stock update (!important for cache consistency)
            productCacheEvictionService.evictProduct(product.getId());

            // Explicitly save updated stock
            productRepository.save(product);
        }

        // 7. Set total amount
        order.setTotalAmount(totalAmount);

        // 8. Save order
        // OrderItems are saved automatically because of CascadeType.ALL
        Order savedOrder = orderRepository.save(order);

        // 9. Convert entity to DTO
        return toDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderById(
            Long id,
            Authentication authentication) {

        // Get logged-in user
        User user = userContextService.getLoggedInUser(authentication);

        // Find order
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ApplicationException(
                                "Order not found with id: " + id,
                                ErrorCode.ORDER_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        )
                );

        // Check ownership
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ApplicationException(
                    "You are not authorized to access this order",
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN
            );
        }

        return toDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getMyOrders(
            Authentication authentication) {

        // Get logged-in user
        User user = userContextService.getLoggedInUser(authentication);

        // Fetch only this user's orders
        return orderRepository.findByUser(user)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private OrderDto toDto(Order order) {

        OrderDto dto = modelMapper.map(
                order,
                OrderDto.class
        );

        // Entity User -> DTO userId
        dto.setUserId(
                order.getUser().getId()
        );

        // OrderItems -> OrderItemDto
        List<OrderItemDto> items =
                order.getOrderItems()
                        .stream()
                        .map(this::toItemDto)
                        .toList();

        dto.setItems(items);

        return dto;
    }

    private OrderItemDto toItemDto(
            OrderItem orderItem) {

        OrderItemDto dto = modelMapper.map(
                orderItem,
                OrderItemDto.class
        );

        // Product entity -> productId
        dto.setProductId(
                orderItem.getProduct().getId()
        );

        // Product entity -> productName
        dto.setProductName(
                orderItem.getProduct().getName()
        );

        // quantity × price
        BigDecimal subtotal =
                orderItem.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        orderItem.getQuantity()
                                )
                        );

        dto.setSubtotal(subtotal);

        return dto;
    }
}