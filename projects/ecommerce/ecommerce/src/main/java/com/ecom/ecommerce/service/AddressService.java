package com.ecom.ecommerce.service;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.dto.AddressDto;
import com.ecom.ecommerce.entity.Address;
import com.ecom.ecommerce.entity.User;
import com.ecom.ecommerce.exception.ApplicationException;
import com.ecom.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserContextService userContextService;
    private final ModelMapper modelMapper;

    @Transactional
    public AddressDto createAddress(
            AddressDto addressDto,
            Authentication authentication) {

        User user = userContextService.getLoggedInUser(authentication);

        Address address = modelMapper.map(addressDto, Address.class);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Transactional(readOnly = true)
    public List<AddressDto> getMyAddresses(
            Authentication authentication) {

        User user = userContextService.getLoggedInUser(authentication);

        return addressRepository.findByUser(user)
                .stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public AddressDto getAddressById(
            Long id,
            Authentication authentication) {

        User user = userContextService.getLoggedInUser(authentication);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Address not found with id: " + id,
                        ErrorCode.ADDRESS_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ApplicationException(
                    "You are not authorized to access this address",
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN);
        }

        return modelMapper.map(address, AddressDto.class);
    }

    @Transactional
    public AddressDto updateAddress(
            Long id,
            AddressDto addressDto,
            Authentication authentication) {

        User user = userContextService.getLoggedInUser(authentication);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Address not found with id: " + id,
                        ErrorCode.ADDRESS_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ApplicationException(
                    "You are not authorized to update this address",
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN);
        }

        modelMapper.map(addressDto, address);

        Address updatedAddress = addressRepository.save(address);

        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    @Transactional
    public void deleteAddress(
            Long id,
            Authentication authentication) {

        User user = userContextService.getLoggedInUser(authentication);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Address not found with id: " + id,
                        ErrorCode.ADDRESS_NOT_FOUND,
                        HttpStatus.NOT_FOUND));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ApplicationException(
                    "You are not authorized to delete this address",
                    ErrorCode.FORBIDDEN,
                    HttpStatus.FORBIDDEN);
        }

        addressRepository.delete(address);
    }

}