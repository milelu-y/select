package com.milelu.service.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author MILELU
 * @date 2021/1/25 17:40
 */
@Data
@Accessors(chain = true)
public class PublishDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "请选择记录!")
    public List<String> ids;

    public String status;

    public Date date;
}
