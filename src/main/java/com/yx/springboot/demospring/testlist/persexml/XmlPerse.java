package com.yx.springboot.demospring.testlist.persexml;

import org.apache.tools.ant.DirectoryScanner;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlPerse {

    @Test
    public void test () throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:xmlfolder");
        this.init(file.getAbsolutePath());
    }

    public void init(String cfgHome) {
        Map<String, Table> meta = new HashMap<String, Table>();
        DirectoryScanner ds = new DirectoryScanner();
        ds.setBasedir(cfgHome);
        ds.setIncludes(new String[] { "metadata*.xml"});
        ds.scan();
        String[] files = ds.getIncludedFiles();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < files.length; i++) {
            sb.append(files[i]).append(";");
            merge(parse(cfgHome + "/" + files[i]), meta);
        }
    }

    private void merge(Map<String, Table> from, Map<String, Table> to) {
        for (Map.Entry<String, Table> entry : from.entrySet()) {
            final String key = entry.getKey();
            if (to.containsKey(key)) {
                continue;
            }
            to.put(key, entry.getValue());
        }
    }

    private Map<String, Table> parse(String path) {
        Map<String, Table> meta = new HashMap<String, Table>();
        try {
            SAXReader builder = new SAXReader();
            Document doc = builder.read(new File(path));
            Element root = doc.getRootElement();
            @SuppressWarnings("unchecked")
            List<Element> childrenList = root.elements("table");
            for (Element child : childrenList) {
                Table table = parseTable(child);
                if (table == null) {
                    continue;
                }
                meta.put(table.getName(), table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return meta;

    }

    private Table parseTable(Element element) {
        @SuppressWarnings("unchecked")
        List<Element> children = element.elements("column");
        List<Column> columns = new ArrayList<Column>(children.size());
        for (Element child : children) {
            Column c = parseColumn(child);
            if (c == null) {
                continue;
            }
            columns.add(c);
        }
        Table table = new Table(columns);
        String name = element.attributeValue("name");
        if (name == null) {
            return null;
        }
        table.setName(name);
        String className = element.attributeValue("class");
        table.setClassName(className);

        return table;
    }

    private Column parseColumn(Element element) {
        Column column = new Column();
        String name = element.attributeValue("name");
        if (name == null) {
            return null;
        }
        column.setName(name);

        column.setAlias(element.elementText("alias"));

        column.setLabel(element.elementText("label"));
        column.setRule(element.elementText("rule"));
        column.setComponent(element.elementText("component"));
        String type = element.elementText("type");
        if (type != null) {
            column.setType(Integer.parseInt(type));
        }
        String sort = element.elementText("sort");
        if (sort != null) {
            column.setSort(Integer.parseInt(sort));
        }
        String datatype = element.elementText("datatype");
        if (datatype != null) {
            column.setDataType(Integer.parseInt(datatype));
        }
        return column;
    }

}
