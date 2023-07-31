package app.spring.service.interfaces.authentication;

import app.spring.response.PaginationResponse;

public interface IUserService {

    PaginationResponse getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

}
