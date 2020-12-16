package com.yx.springboot.demospring.workflowpaint;

import java.util.List;

/**
 * @author yuanxin
 */
public class PaintWorkFlowNew {

    public void getEndBranchNode (WorkflowNode startBranchNode) {
        WorkflowNode preWorkflowNode = startBranchNode.getPreNodeList().get(0);
        startBranchNode.setXIndex(preWorkflowNode.getXIndex() + preWorkflowNode.getWidth() + NodeContext.LINE_TRANSVERSE);
        int yIndex = preWorkflowNode.getYIndex() + (preWorkflowNode.getHeight() / 2) - (startBranchNode.getHeight() / 2);
        //分支上刚开始的分支开始节点
        if (preWorkflowNode.isBeginBranchNode()) {
            //TODO 需要特殊处理，后续再作补充
            startBranchNode.setYIndex(0);
        } else {
            startBranchNode.setYIndex(yIndex);
        }
        //设置它的下一级节点坐标
        List<WorkflowNode> nextNodeList = startBranchNode.getNextNodeList();
        int halfSize = nextNodeList.size() / 2;
        for (int i = 0; i < nextNodeList.size(); i++) {
            WorkflowNode workflowNode = nextNodeList.get(i);
            //表示偶数个
            if (nextNodeList.size() % 2 == 0) {
                //判断竖线，用此方式可以动态计算奇数个并行节点上每个节点的Y轴需要增加或者减少的线条长度
                int commonVertical = (i - halfSize) * NodeContext.LINE_VERTICAL + NodeContext.LINE_VERTICAL / 2;
                workflowNode.setXIndex(startBranchNode.getXIndex() + startBranchNode.getWidth() / 2 + NodeContext.LINE_TRANSVERSE);
                if (i < halfSize) {
                    workflowNode.setYIndex(startBranchNode.getYIndex() - workflowNode.getHeight() / 2 + commonVertical);
                    //节点在下方
                } else {
                    workflowNode.setYIndex(startBranchNode.getYIndex() + startBranchNode.getHeight() - workflowNode.getHeight() / 2 + commonVertical);
                }
            } else {
                //判断竖线，用此方式可以动态计算奇数个并行节点上每个节点的Y轴需要增加或者减少的线条长度
                int commonVertical = (i - halfSize) * NodeContext.LINE_VERTICAL;
                workflowNode.setXIndex(startBranchNode.getXIndex() + startBranchNode.getWidth() / 2 + NodeContext.LINE_TRANSVERSE);
                //节点在上方
                if (i < halfSize) {
                    workflowNode.setYIndex(startBranchNode.getYIndex() - workflowNode.getHeight() / 2 + commonVertical);
                    //节点在中间
                } else if (i == halfSize) {
                    workflowNode.setYIndex(startBranchNode.getYIndex() + startBranchNode.getHeight() / 2 - workflowNode.getHeight() / 2 + commonVertical);
                    //节点在下方
                } else {
                    workflowNode.setYIndex(startBranchNode.getYIndex() + startBranchNode.getHeight() - workflowNode.getHeight() / 2 + commonVertical);
                }
            }
            //分支开始节点下一级不可能是分支结束节点，所以肯定是普通节点或者开始分支节点
            if (!workflowNode.isBeginBranchNode()) {
                WorkflowNode node = workflowNode.getNextNodeList().get(0);
                if (node.isEndBranchNode()) {
                    //TODO 分支结束节点，根据最靠右的节点计算X坐标，根据最高、最低计算Y坐标

                    //计算结束节点
                }
                if (node.isBeginBranchNode()) {
                    getEndBranchNode(node);
                }
            } else {
                getEndBranchNode(workflowNode);
            }
        }
    }
}
