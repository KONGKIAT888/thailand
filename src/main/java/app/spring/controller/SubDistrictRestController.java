package app.spring.controller;

import app.spring.request.SubDistrictRequest;
import app.spring.response.PaginationResponse;
import app.spring.response.SubDistrictResponse;
import app.spring.service.interfaces.ISubDistrictService;
import app.spring.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubDistrictRestController {

    private final ISubDistrictService subDistrictService;

    public SubDistrictRestController(ISubDistrictService subDistrictService) {
        this.subDistrictService = subDistrictService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/sub-district")
    public ResponseEntity<SubDistrictRequest> create(@Valid @RequestBody SubDistrictRequest subDistrictRequest) {
        return new ResponseEntity<>(subDistrictService.create(subDistrictRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/sub-district/{id}")
    public ResponseEntity<SubDistrictRequest> update(@Valid @RequestBody SubDistrictRequest subDistrictRequest,
                                                     @PathVariable(name = "id") long id) {
        SubDistrictRequest updateData = subDistrictService.update(subDistrictRequest, id);
        return new ResponseEntity<>(updateData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/v1/sub-district/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id) {
        subDistrictService.deleteById(id);
        return new ResponseEntity<>("This Data entity deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/v1/sub-district")
    public PaginationResponse getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
                                     @RequestParam(value = "search", required = false) String keyword,
                                     @RequestParam(value = "all", defaultValue = AppConstants.DEFAULT_GET_ALL, required = false) Boolean all) {
        return subDistrictService.getAll(pageNo, pageSize, sortBy, sortDir, keyword, all);
    }

    @GetMapping("/v1/sub-district/{name}")
    public ResponseEntity<SubDistrictResponse> getById(@PathVariable(name = "name") String name,
                                                       @PathVariable(name = "name") String nameEnglish) {
        return ResponseEntity.ok(subDistrictService.getByNameOrNameEnglish(name, nameEnglish));
    }

}
