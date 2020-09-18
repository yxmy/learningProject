package com.yx.springboot.demospring.workflowpaint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PaintTest {

    public static void main(String[] args) throws IOException {
        PaintWorkFlow flow = new PaintWorkFlow();
        List<WorkflowNode> nodes = flow.test();
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

        nodes.forEach(node->{
            //画图形
            graphics.setColor(Color.black);
            graphics.drawString(node.getName(),node.getXIndex()+10,node.getYIndex()+node.getHeight()/2);
            graphics.drawRect(node.getXIndex(), node.getYIndex(), node.getWidth(), node.getHeight());
            //划线
            //获取该节点划线的起始点
            int fromX;
            int fromY;
            graphics.setColor(Color.blue);
            //根据情况获取划线的终止点
            if (node.isIfBeginBranchNode()) {
                fromX = node.getXIndex() + node.getWidth();
                fromY = node.getYIndex() + node.getHeight() / 2;
                List<WorkflowNode> NextNodeList = node.getNextNodeList();
                NextNodeList.forEach(nextNode->{
                    int toX = nextNode.getXIndex();
                    int toY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                    graphics.drawLine(fromX, fromY, toX, toY);
                });
            } else if (node.isIfEndBranchNode()){
                //反向画线，从终点到起点，画的是分支节点指向分支的线
                fromX = node.getXIndex();
                fromY = node.getYIndex() + node.getHeight() / 2;
                List<WorkflowNode> preNodeList = node.getPreNodeList();
                preNodeList.forEach(preNode->{
                    int toX = preNode.getXIndex() + preNode.getWidth();
                    int toY = preNode.getYIndex() + preNode.getHeight() / 2;
                    graphics.drawLine(fromX, fromY, toX, toY);
                });
                //还需要画流程后面的线，改节点指向其他节点的线，分支结束节点不可能是分支开始节点
                int fromX2 = node.getXIndex() + node.getWidth();
                int fromY2 = node.getYIndex() + node.getHeight() / 2;
                WorkflowNode nextNode = node.getNextNodeList().get(0);
                int toX = nextNode.getXIndex();
                int toY = nextNode.getYIndex() + nextNode.getHeight() / 2;
                graphics.drawLine(fromX2, fromY2, toX, toY);
            } else {
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

        });

//        /*
//         * 画线 x,y是坐标，定义线段的两个坐标点
//         */
//        graphics.setColor(Color.white);
//        int x=100,y=100,x1=100,y1=y;
//        graphics.drawLine(x,y,x+x1,y1);
//        /*
//         *画出一个折线
//         */
//        int[] xPoints = {100,100,250,250};
//        int[] yPoints = {180,150,150,180};
//        graphics.drawPolyline(xPoints, yPoints, 4);
//        /*
//         * 画出一个闭合多边形(三角形)
//         */
//        int[] xPoints1 = {100,100,200};
//        int[] yPoints1 = {240,320,280};
//        graphics.drawPolygon(xPoints1, yPoints1, 3);
//        /*
//         * 画出一个闭合多边形(菱形)
//         */
//        int[] xPoints2 = {240,300,360,300};
//        int[] yPoints2 = {280,240,280,320};
//        graphics.drawPolygon(xPoints2, yPoints2, 4);
//        graphics.setColor(Color.ORANGE);
//        graphics.fillPolygon(xPoints2, yPoints2, 4);
//        /*
//         *绘制一个椭圆形
//         */
//        graphics.setColor(Color.GREEN);
//        int xOval=100,yOval=360;
//        graphics.drawOval(xOval, yOval, 200, 100);

        /*
         *绘制一个矩形
         */
        //graphics.setColor(Color.GRAY);//--设置矩形边框颜色 。GREEN:绿色；  红色：RED;   灰色：GRAY
//        int xRect=240,yRect=360;
//        graphics.drawRect(xRect, yRect, 200, 100);
//
//        //设置文字颜色
//        graphics.setColor(new Color( 20+random.nextInt(100),  20+random.nextInt(100),  20+random.nextInt(100) ));
//        //设置文字内容、位置
//        graphics.drawString("直线",100+50,100-5);
//        graphics.drawString("折线", 200, 150-5);
//        graphics.drawString("空心三角形", 110, 280);
//        graphics.drawString("实心菱形", 300-20, 280);
//        graphics.drawString("椭圆形", 100+50, 360+50);
//        graphics.drawString("矩形", 240+50, 360+50);
        //graphics.drawString("错误的背景颜色", 100, 540);

        //graphics.setPaintMode();
        //graphics.translate(400, 600);

        graphics.dispose();//释放此图形的上下文并释放它所使用的所有系统资源

        ImageIO.write(image,"JPEG",new File("D:\\workflow.jpg"));
    }
}
