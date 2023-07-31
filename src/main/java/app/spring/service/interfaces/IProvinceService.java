package app.spring.service.interfaces;

import app.spring.request.ProvinceRequest;
import app.spring.response.PaginationResponse;
import app.spring.response.ProvinceResponse;

public interface IProvinceService {

    ProvinceRequest create(ProvinceRequest provinceRequest);

    ProvinceRequest update(ProvinceRequest provinceRequest, long id);

    void deleteById(long id);

    PaginationResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword, Boolean all);

    ProvinceResponse getByNameOrNameEnglish(String name, String nameEnglish);

}