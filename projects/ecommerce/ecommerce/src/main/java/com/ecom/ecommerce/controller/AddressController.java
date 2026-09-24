package com.ecom.ecommerce.controller;

import com.ecom.ecommerce.dto.AddressDto;
import com.ecom.ecommerce.dto.ApiResponse;
import com.ecom.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddressDto>> createAddress(
            @Valid @RequestBody AddressDto addressDto,
            Authentication authentication) {

        AddressDto createdAddress =
                addressService.createAddress(addressDto, authentication);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Address created successfully",
                                "/api/addresses",
                                createdAddress
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressDto>>> getMyAddresses(
            Authentication authentication) {

        List<AddressDto> addresses =
                addressService.getMyAddresses(authentication);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Addresses fetched successfully",
                        "/api/addresses",
                        addresses
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressDto>> getAddressById(
            @PathVariable Long id,
            Authentication authentication) {

        AddressDto address =
                addressService.getAddressById(id, authentication);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address fetched successfully",
                        "/api/addresses/" + id,
                        address
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressDto>> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressDto addressDto,
            Authentication authentication) {

        AddressDto updatedAddress =
                addressService.updateAddress(
                        id,
                        addressDto,
                        authentication
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address updated successfully",
                        "/api/addresses/" + id,
                        updatedAddress
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long id,
            Authentication authentication) {

        addressService.deleteAddress(id, authentication);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Address deleted successfully",
                        "/api/addresses/" + id,
                        null
                )
        );
    }
}