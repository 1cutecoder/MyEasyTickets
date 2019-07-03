package com.stylefeng.guns.rest.modular.film.vo;

import com.cskaoyan.model.CategoryVO;
import com.cskaoyan.model.SourceVO;
import com.cskaoyan.model.YearVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class FilmConditionVO implements Serializable {
    private List<CategoryVO> catInfo;
    private List<SourceVO> sourceInfo;
    private List<YearVO> yearInfo;
}
