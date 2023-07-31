package app.spring.controller;

import app.spring.request.ProvinceRequest;
import app.spring.response.PaginationResponse;
import app.spring.response.ProvinceResponse;
import app.spring.service.interfaces.IProvinceService;
import app.spring.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProvinceRestController {

    private final IProvinceService provinceService;

    public ProvinceRestController(IProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/province")
    public ResponseEntity<ProvinceRequest> create(@Valid @RequestBody ProvinceRequest provinceRequest) {
        return new ResponseEntity<>(provinceService.create(provinceRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/v1/province/{id}")
    public ResponseEntity<ProvinceRequest> update(@Valid @RequestBody ProvinceRequest provinceRequest,
                                                  @PathVariable(name = "id") long id) {
        ProvinceRequest updateData = provinceService.update(provinceRequest, id);
        return new ResponseEntity<>(updateData, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/v1/province/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") long id) {
        provinceService.deleteById(id);
        return new ResponseEntity<>("This Data entity deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/v1/province")
    public PaginationResponse getAll(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
                                     @RequestParam(value = "search", required = false) String keyword,
                                     @RequestParam(value = "all", defaultValue = AppConstants.DEFAULT_GET_ALL, required = false) Boolean all) {
        return provinceService.getAll(pageNo, pageSize, sortBy, sortDir, keyword, all);
    }

    @GetMapping("/v1/province/{name}")
    public ResponseEntity<ProvinceResponse> getById(@PathVariable(name = "name") String name,
                                                    @PathVariable(name = "name") String nameEnglish) {
        return ResponseEntity.ok(provinceService.getByNameOrNameEnglish(name, nameEnglish));
    }

}
