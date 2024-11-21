package com.melly.myweb.commons.inif;

import com.melly.myweb.commons.dto.CUDInfoDto;

public interface IServiceCRUD<T>{
    T insert(CUDInfoDto cudInfoDto, T dto);
    T update(CUDInfoDto cudInfoDto, T dto);
    Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, T dto);
    Boolean deleteById(Long id);
    T findById(Long id);


}
