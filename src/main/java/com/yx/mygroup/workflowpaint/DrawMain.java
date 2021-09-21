package com.yx.mygroup.workflowpaint;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class DrawMain {

    @Test
    public void drawMain() throws IOException {
        String xmlFileName = "workflow4.xml";
        PaintWorkFlow flow = new PaintWorkFlow();
        log.info("开始获取xml信息并解析为node集合。。。。。");
        List<WorkflowNode> nodes = flow.getNodesByFileName(xmlFileName);
        log.info("解析完成。。。。。集合数量为：【{}】", nodes.size());
        log.info("开始绘制流程图。。。。");
        //绘制宽=480，长=640的图板
        int width=NodeContext.CANVAS_WIDTH,height=NodeContext.CANVAS_HEIGHT;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //获取图形上下文,graphics想象成一个画笔
        Graphics2D graphics = (Graphics2D)image.getGraphics();
        //消除线条锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //对指定的矩形区域填充颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        drawFlow(graphics, nodes);
        graphics.dispose();//释放此图形的上下文并释放它所使用的所有系统资源
        ImageIO.write(image,"JPEG",new File("D:\\workflow.jpg"));
        log.info("流程图绘制完成！！！");
    }

    /**
     * 画图方法
     * @param graphics
     * @param nodes
     */
    private void drawFlow (Graphics2D graphics, List<WorkflowNode> nodes) {
        for (WorkflowNode node : nodes) {
            //画图形
            graphics.setColor(Color.black);
            graphics.drawString(node.getName(),node.getXIndex()+10,node.getYIndex()+node.getHeight()/2);
            if (node.isEndBranchNode() || node.isBeginBranchNode()) {
                graphics.drawOval(node.getXIndex(), node.getYIndex(), node.getWidth(), node.getHeight());
            } else {
                graphics.drawRect(node.getXIndex(), node.getYIndex(), node.getWidth(), node.getHeight());
            }
            //划线
            //获取该节点划线的起始点
            int fromX;
            int fromY;
            graphics.setColor(Color.blue);
            //根据情况获取划线的终止点
            if (node.isBeginBranchNode()) {
                List<WorkflowNode> nextNodeList = node.getNextNodeList();
                int halfSize = nextNodeList.size() / 2;
                for (int i = 0; i < nextNodeList.size(); i++) {
                    WorkflowNode nextNode = nextNodeList.get(i);
                    int nextNodeY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                    int nodeY = node.getYIndex() + node.getHeight() / 2;
                    int [] xIndexArr;
                    int[] yIndexArr;
                    //节点在上面
                    if (nextNodeY < nodeY) {
                        xIndexArr = new int[]{node.getXIndex() + node.getWidth() / 2, node.getXIndex() + node.getWidth() / 2, nextNode.getXIndex()};
                        yIndexArr = new int[]{node.getYIndex(), nextNode.getYIndex() + nextNode.getHeight() / 2, nextNode.getYIndex() + nextNode.getHeight() / 2};
                        graphics.drawPolyline(xIndexArr, yIndexArr, 3);
                        //节点在下方
                    } else if (nextNodeY > nodeY) {
                        xIndexArr = new int [] {node.getXIndex() + node.getWidth() / 2, node.getXIndex() + node.getWidth() / 2, nextNode.getXIndex()};
                        yIndexArr = new int[]{node.getYIndex() + node.getHeight(), nextNode.getYIndex() + nextNode.getHeight() / 2, nextNode.getYIndex() + nextNode.getHeight() / 2};
                        graphics.drawPolyline(xIndexArr, yIndexArr, 3);
                    } else {
                        fromX = node.getXIndex() + node.getWidth();
                        fromY = node.getYIndex() + node.getHeight() / 2;
                        int toX = nextNode.getXIndex();
                        int toY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                        graphics.drawLine(fromX, fromY, toX, toY);
                    }

                }
            } else if (node.isEndBranchNode()){
                List<WorkflowNode> preNodeList = node.getPreNodeList();
                int halfSize = preNodeList.size() / 2;
                for (int i = 0; i < preNodeList.size(); i++) {
                    WorkflowNode preNode = preNodeList.get(i);
                    int preNodeY = preNode.getYIndex() + preNode.getHeight() / 2;
                    int nodeY = node.getYIndex() + node.getHeight() / 2;
                    int [] xIndexArr = new int [] {preNode.getXIndex() + preNode.getWidth(), node.getXIndex() + node.getWidth() / 2, node.getXIndex() + node.getWidth() / 2};
                    int [] yIndexArr;
                    //节点在上面
                    if (preNodeY < nodeY) {
                        yIndexArr = new int [] {preNode.getYIndex() + preNode.getHeight() / 2, preNode.getYIndex() + preNode.getHeight() / 2, node.getYIndex()};
                        graphics.drawPolyline(xIndexArr, yIndexArr, 3);
                        //节点在下方
                    } else if (preNodeY > nodeY) {
                        yIndexArr = new int [] {preNode.getYIndex() + preNode.getHeight() / 2, preNode.getYIndex() + preNode.getHeight() / 2, node.getYIndex() + node.getHeight()};
                        graphics.drawPolyline(xIndexArr, yIndexArr, 3);
                    } else {
                        fromX = preNode.getXIndex() + preNode.getWidth();
                        fromY = preNode.getYIndex() + preNode.getHeight() / 2;
                        int toX = node.getXIndex();
                        int toY = node.getYIndex() + node.getHeight() / 2;
                        graphics.drawLine(fromX, fromY, toX, toY);
                    }

                }
                //还需要画分支节点流程后面的线，该节点指向其他节点的线，分支结束节点不可能是分支开始节点
                WorkflowNode nextNode = node.getNextNodeList().get(0);
                if (nextNode.isEndBranchNode()) {
                    continue;
                }
                int fromX2 = node.getXIndex() + node.getWidth();
                int fromY2 = node.getYIndex() + node.getHeight() / 2;
                int toX = nextNode.getXIndex();
                int toY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                graphics.drawLine(fromX2, fromY2, toX, toY);
            } else {
                //上述分支终节点已经向preNode节点划线了，所以不再处理
                if (node.getNextNodeList() != null) {
                    boolean ifEndBranchNode = node.getNextNodeList().get(0).isEndBranchNode();
                    if (ifEndBranchNode) {
                        continue;
                    }
                }
                fromX = node.getXIndex() + node.getWidth();
                fromY = node.getYIndex() + node.getHeight() / 2;
                //终节点的next为空
                if (node.getNextNodeList() != null) {
                    WorkflowNode nextNode = node.getNextNodeList().get(0);
                    int toX = nextNode.getXIndex();
                    int toY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                    graphics.drawLine(fromX, fromY, toX, toY);
                }
            }

        }
    }
}
