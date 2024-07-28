package com.utcl.ccnfservice.cement.controller.swagger;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import com.utcl.ccnfservice.cement.transaction.entity.CCnfCementLoi;
import com.utcl.dto.ccnf.CCnfLoiDto;
import com.utcl.dto.ccnf.requestResponce.CCnfLoiRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Generated;

@Validated
@Tag(name = "ccnftransaction", description = "the CcnfTransaction API")
public interface TransactionApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }
    @Operation(
        operationId = "getCCnfLoiDetailsById",
        summary = "CCNF endpoint that returns LOI details by Id.",
        tags = { "vendor" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Literally returns LOI Details.", content = {
                @Content(mediaType = "Application/JSON")
            })
        }
    )
    
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/{id}",
        produces = { "Application/JSON" }
    )
    default ResponseEntity<CCnfLoiDto> getCCnfLoiDetailsById(@PathVariable int id){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    @Operation(
            operationId = "getCCnfLoiDetails",
            summary = "CCNF endpoint that returns all LOI details.",
            tags = { "All ccnfTransactions" },
            responses = {
                @ApiResponse(responseCode = "200", description = "Literally returns All LOI Details.", content = {
                    @Content(mediaType = "Application/JSON")
                })
            }
        )
        
        @RequestMapping(
            method = RequestMethod.GET,
            produces = { "Application/JSON" }
        )
        default ResponseEntity<List<CCnfCementLoi>> getCCnfLoiDetails(){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    
    default Optional<NativeWebRequest> postRequest() {
        return Optional.empty();
    }
    @Operation(
            operationId = "addCcnfLoiDetails",
            summary = "CCNF endpoint that add all LOI details.",
            tags = { "All ccnfTransactions" },
            responses = {
                @ApiResponse(responseCode = "200", description = "Literally add All LOI Details.", content = {
                    @Content(mediaType = "Application/JSON")
                })
            }
        )
        
        @RequestMapping(
            method = RequestMethod.POST,
            produces = { "Application/JSON" }
        )
        default ResponseEntity<CCnfLoiDto> addCcnfLoiDetails(@RequestBody CCnfLoiRequest cCnfLoiDto){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    
}
