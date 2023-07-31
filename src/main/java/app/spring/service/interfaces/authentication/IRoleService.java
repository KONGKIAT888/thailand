package app.spring.service.interfaces.authentication;

import app.spring.response.PaginationResponse;

public interface IRoleService {

    PaginationResponse getAllRoles(int pageNo, int pageSize, String sortBy, String sortDir);

}
