package app.spring.controller;

import app.spring.request.DistrictRequest;
import app.spring.response.DistrictResponse;
import app.spring.response.PaginationResponse;
import app.spring.service.interfaces.IDistrictService;
import app.spring.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class DistrictRestController {

    private final IDistrictService districtService;

    public DistrictRestController(IDistrictService districtService) {
        this.districtService = districtService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/district")
    public ResponseEntity<DistrictRequest> create(@Valid @RequestBody DistrictRequest districtRequest) {
        return new ResponseEntity<>(districtService.create(districtRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/district/{id}")
    public ResponseEntity<DistrictRequest> update(@Valid @RequestBody DistrictRequest districtRequest,
                                                  @PathVariable(name = "id") long id) {
        DistrictRequest updateData = districtService.update(districtRequest, id);
        return new ResponseEntity<>(updateData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/v1/district/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id) {
        districtService.deleteById(id);
        return new ResponseEntity<>("This Data entity deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/v1/district")
    public PaginationResponse getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
                                     @RequestParam(value = "search", required = false) String keyword,
                                     @RequestParam(value = "all", defaultValue = AppConstants.DEFAULT_GET_ALL, required = false) Boolean all) {
        return districtService.getAll(pageNo, pageSize, sortBy, sortDir, keyword, all);
    }

    @GetMapping("/v1/district/{name}")
    public ResponseEntity<DistrictResponse> getById(@PathVariable(name = "name") String name,
                                                    @PathVariable(name = "name") String nameEnglish) {
        return ResponseEntity.ok(districtService.getByNameOrNameEnglish(name, nameEnglish));
    }

}
