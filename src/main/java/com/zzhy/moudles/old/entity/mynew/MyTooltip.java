package com.zzhy.moudles.old.entity.mynew;

import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.Trigger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author majt
 */
@Getter
@Setter
@NoArgsConstructor
public class MyTooltip extends Tooltip {

    public MyTooltip(Trigger trigger) {
        if (null == trigger){
            super.trigger(Trigger.axis);
        }
    }
}
