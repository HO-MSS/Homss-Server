package com.como.server.model;

import com.como.server.common.modle.TimeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("solve_problem")
public class SolveProblem extends TimeModel {

    private Long solveProblemId;
    private Long memberId;
    private Integer level;
    private Integer solveCount;

}
