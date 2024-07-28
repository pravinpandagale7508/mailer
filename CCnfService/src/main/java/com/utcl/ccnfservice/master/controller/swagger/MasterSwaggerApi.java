package com.utcl.ccnfservice.master.controller.swagger;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import com.utcl.dto.ccnf.CCnfLoiDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@Tag(name = "agent", description = "the Agency API")
public interface MasterSwaggerApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }
    @Operation(
        operationId = "getAll",
        summary = "Agency endpoint that returns agency details.",
        tags = { "agent" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Literally returns Agency.", content = {
                @Content(mediaType = "Application/JSON")
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/{id}",
        produces = { "Application/JSON" }
    )
    default ResponseEntity<CCnfLoiDto> getAllAgencies(@PathVariable int id){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
