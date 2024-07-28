package com.utcl.controller.swagger;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import com.utcl.dto.VendorDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;

@Validated
@Tag(name = "vendor", description = "the vendor API")
public interface VendorApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }
    @Operation(
        operationId = "getVendorById",
        summary = "vendor endpoint that returns vendor.",
        tags = { "vendor" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Literally returns Vendor.", content = {
                @Content(mediaType = "Application/JSON")
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/{id}",
        produces = { "Application/JSON" }
    )
    default ResponseEntity<VendorDTO> getVendorById(@PathVariable int id){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
