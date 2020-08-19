package com.pci.hjmos.cache.module.SystemLineStation;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author By ZengPeng
 * @Description
 * @date in  2020/7/24 13:45
 * @Modified By
 */
@Accessors(chain = true)
@Data
public class SystemLineStation implements Serializable {
    private int stationId;
    private int lineId;
    private String stationName;
}
