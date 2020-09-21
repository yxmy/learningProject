package com.yx.springboot.demospring.workflowpaint;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seeyon_yuanxin
 */
@Slf4j
public class PaintWorkFlow {

    public List<WorkflowNode> getNodesByFileName(String fileName) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:xmlfolder/" + fileName);
        List<WorkflowNode> workflowNodes = parseXml(file);
        setIndexForNode(workflowNodes);
        return workflowNodes;
    }

    @SuppressWarnings("unchecked")
    public List<WorkflowNode> parseXml(File file){
        Map<Integer, WorkflowNode> id2Node = new HashMap<>(16);
        try {
            SAXReader builder = new SAXReader();
            Document doc = builder.read(file);
            Element root = doc.getRootElement();
            //首先解析出node标签，并保存
            List<Element> nodes = root.elements("node");
            nodes.forEach(node->{
                String name = node.attributeValue("name");
                int id = Integer.parseInt(node.attributeValue("id"));
                WorkflowNode workflowNode = new WorkflowNode();
                workflowNode.setId(id);
                workflowNode.setName(name);
                id2Node.put(id, workflowNode);
            });
            //解析line标签，并为node节点赋值
            List<Element> lines = root.elements("line");
            lines.forEach(line->{
                int from = Integer.parseInt(line.attributeValue("from"));
                int to = Integer.parseInt(line.attributeValue("to"));
                WorkflowNode fromNode = id2Node.get(from);
                WorkflowNode toNode = id2Node.get(to);
                //为from节点设置下一个节点
                List<WorkflowNode> nextNodeList = fromNode.getNextNodeList();
                if (nextNodeList == null) {
                    nextNodeList = new ArrayList<>();
                    //增加此判断是为了解决可能其他line已经设置过该节点宽高问题
                    if (!fromNode.isBeginBranchNode() && !fromNode.isEndBranchNode()) {
                        fromNode.setWidth(NodeContext.NODE_WIDTH);
                        fromNode.setHeight(NodeContext.NODE_HEIGHT);
                    }
                } else {
                    fromNode.setBeginBranchNode(true);
                    fromNode.setWidth(NodeContext.BRANCH_NODE_WIDTH);
                    fromNode.setHeight(NodeContext.BRANCH_NODE_HEIGHT);
                }
                nextNodeList.add(toNode);
                fromNode.setNextNodeList(nextNodeList);
                //为to节点设置上一个节点
                List<WorkflowNode> preNodeList = toNode.getPreNodeList();
                if (preNodeList == null) {
                    preNodeList = new ArrayList<>();
                    //增加此判断是为了解决可能其他line已经设置过该节点宽高问题
                    if (!toNode.isBeginBranchNode() && !toNode.isEndBranchNode()) {
                        toNode.setWidth(NodeContext.NODE_WIDTH);
                        toNode.setHeight(NodeContext.NODE_HEIGHT);
                    }
                } else {
                    toNode.setEndBranchNode(true);
                    toNode.setWidth(NodeContext.BRANCH_NODE_WIDTH);
                    toNode.setHeight(NodeContext.BRANCH_NODE_HEIGHT);
                }
                preNodeList.add(fromNode);
                toNode.setPreNodeList(preNodeList);
            });
        } catch (DocumentException e) {
            log.info(e.getMessage());
        }
        //最后返回处理好的node集合
        return new ArrayList<>(id2Node.values());
    }

    /**
     * 为节点设置X,Y坐标
     * @param nodes 节点集合
     */
    public void setIndexForNode (List<WorkflowNode> nodes) {
        for (WorkflowNode node : nodes) {
            //表示此节点已经设置过
            if (node.getXIndex() != 0 || node.getYIndex() != 0) {
                continue;
            }
            //头结点
            if (node.getPreNodeList() == null) {
                node.setXIndex(NodeContext.ROOT_NODE_X_INDEX);
                int yIndex = (NodeContext.CANVAS_HEIGHT / 2) + (node.getHeight() / 2);
                node.setYIndex(yIndex);
            } else {
                if (node.isBeginBranchNode()) {
                    WorkflowNode preWorkflowNode = node.getPreNodeList().get(0);
                    node.setXIndex(preWorkflowNode.getXIndex() + preWorkflowNode.getWidth() + NodeContext.LINE_TRANSVERSE);
                    int yIndex = preWorkflowNode.getYIndex() + (preWorkflowNode.getHeight() / 2) - (node.getHeight() / 2);
                    node.setYIndex(yIndex);
                    //设置它的下一级节点坐标
                    List<WorkflowNode> nextNodeList = node.getNextNodeList();
                    int halfSize = nextNodeList.size() / 2;
                    for (int i = 0; i < nextNodeList.size(); i++) {
                        WorkflowNode workflowNode = nextNodeList.get(i);
                        //表示偶数个
                        if (nextNodeList.size() % 2 == 0) {
                            //判断竖线，用此方式可以动态计算奇数个并行节点上每个节点的Y轴需要增加或者减少的线条长度
                            int commonVertical = (i - halfSize) * NodeContext.LINE_VERTICAL + NodeContext.LINE_VERTICAL / 2;
                            workflowNode.setXIndex(node.getXIndex() + node.getWidth() / 2 + NodeContext.LINE_TRANSVERSE);
                            if (i < halfSize) {
                                workflowNode.setYIndex(node.getYIndex() - workflowNode.getHeight() / 2 + commonVertical);
                            //节点在下方
                            } else {
                                workflowNode.setYIndex(node.getYIndex() + node.getHeight() - workflowNode.getHeight() / 2 + commonVertical);
                            }
                        } else {
                            //判断竖线，用此方式可以动态计算奇数个并行节点上每个节点的Y轴需要增加或者减少的线条长度
                            int commonVertical = (i - halfSize) * NodeContext.LINE_VERTICAL;
                            workflowNode.setXIndex(node.getXIndex() + node.getWidth() / 2 + NodeContext.LINE_TRANSVERSE);
                            //节点在上方
                            if (i < halfSize) {
                                workflowNode.setYIndex(node.getYIndex() - workflowNode.getHeight() / 2 + commonVertical);
                            //节点在中间
                            } else if (i == halfSize) {
                                workflowNode.setYIndex(node.getYIndex() + node.getHeight() / 2 - workflowNode.getHeight() / 2 + commonVertical);
                            //节点在下方
                            } else {
                                workflowNode.setYIndex(node.getYIndex() + node.getHeight() - workflowNode.getHeight() / 2 + commonVertical);
                            }
                        }
                    }
                } else if (node.isEndBranchNode()) {
                    List<WorkflowNode> preNodeList = node.getPreNodeList();
                    ArrayList<WorkflowNode> minAndMaxYNodes = getMinAndMaxYNodes(preNodeList);
                    WorkflowNode firstNode = minAndMaxYNodes.get(0);
                    WorkflowNode lastNode = minAndMaxYNodes.get(minAndMaxYNodes.size() - 1);
                    int halfIndex = ((firstNode.getYIndex() + firstNode.getHeight() / 2) + (lastNode.getYIndex() + lastNode.getHeight() / 2)) / 2;
                    node.setYIndex(halfIndex - node.getHeight() / 2);
                    WorkflowNode maxXIndexNode = getMaxXIndexNode(preNodeList);
                    node.setXIndex(maxXIndexNode.getXIndex() + maxXIndexNode.getWidth() + NodeContext.LINE_TRANSVERSE - node.getWidth() / 2);
                } else {
                    WorkflowNode preWorkflowNode = node.getPreNodeList().get(0);
                    node.setXIndex(preWorkflowNode.getXIndex() + preWorkflowNode.getWidth() + NodeContext.LINE_TRANSVERSE);
                    if (preWorkflowNode.isEndBranchNode()) {
                        node.setYIndex(preWorkflowNode.getYIndex() + preWorkflowNode.getHeight() / 2 - node.getHeight() / 2);
                    } else {
                        node.setYIndex(preWorkflowNode.getYIndex());
                    }
                }
            }
        }
    }

    /**
     * 获取集合中X坐标最大的节点
     * @param nodes
     * @return
     */
    private WorkflowNode getMaxXIndexNode (List<WorkflowNode> nodes) {
        int maxIndex = 0;
        WorkflowNode maxXIndexNode = null;
        for (WorkflowNode node : nodes) {
            if (node.getXIndex() > maxIndex) {
                maxIndex = node.getXIndex();
                maxXIndexNode = node;
            }
        }
        return maxXIndexNode;
    }

    /**
     * 获取集合中Y坐标最大和最小的节点
     * @param nodes
     * @return
     */
    private ArrayList<WorkflowNode> getMinAndMaxYNodes (List<WorkflowNode> nodes) {
        WorkflowNode[] arr = new WorkflowNode[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            arr[i] = nodes.get(i);
        }
        //选择排序
        int k;
        WorkflowNode tmp;
        for(int i=0;i<arr.length-1;i++){
            k = i;
            for(int j = i; j <arr.length; j++){
                if(arr[j].getYIndex() < arr[k].getYIndex()){
                    k = j;
                }
            }
            tmp = arr[i];
            arr[i] = arr[k];
            arr[k] = tmp;
        }
        return new ArrayList<WorkflowNode>(2){{
            add(arr[0]);
            add(arr[arr.length - 1]);
        }};
    }

}
