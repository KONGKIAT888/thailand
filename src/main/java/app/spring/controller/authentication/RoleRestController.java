package app.spring.controller.authentication;

import app.spring.response.PaginationResponse;
import app.spring.service.interfaces.authentication.IRoleService;
import app.spring.utils.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleRestController {

    private final IRoleService roleService;

    public RoleRestController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public PaginationResponse getAllRoles(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return roleService.getAllRoles(pageNo, pageSize, sortBy, sortDir);
    }

}
