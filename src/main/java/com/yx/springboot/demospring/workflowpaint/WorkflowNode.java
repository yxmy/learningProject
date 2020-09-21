package com.yx.springboot.demospring.workflowpaint;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author seeyon_yuanxin
 */
@Getter
@Setter
public class WorkflowNode {

    /**
     * 节点id
     */
    private int id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点的X坐标
     */
    private int xIndex;

    /**
     * 节点的Y坐标
     */
    private int yIndex;

    /**
     * 节点的宽度
     */
    private int width;

    /**
     * 节点的高度
     */
    private int height;

    /**
     * 是否是开始分支节点，就是并行节点的出发
     */
    private boolean isBeginBranchNode = false;

    /**
     * 是否是结束分支节点，就是并行节点的结束
     */
    private boolean isEndBranchNode = false;

    /**
     * 上一个节点
     */
    private List<WorkflowNode> preNodeList;

    /**
     * 下一个节点
     */
    private List<WorkflowNode> nextNodeList;

}
