/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.utils;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CommentHelper {

    public final static CommentHelper INSTANCE = new CommentHelper();

    private static Map<String, CommentInner> comments;

    private CommentHelper() {
        comments = new HashMap<>();
        try {
            parse();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void parse() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        factory.setValidating(false);
        // $NON-NLS-1$
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("mybatis-generator-docs.xml")) {

            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = null;
            try {
                document = builder.parse(is);
            }
            catch (Exception e) {
                throw new RuntimeException("can't parse docs.xml");
            }
            if (document == null) {
                throw new RuntimeException("can't parse docs.xml");
            }

            Element rootNode = document.getDocumentElement();
            parseRootNode(rootNode);
        }
    }

    private void parseRootNode(Element rootNode) {
        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);

            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if ("doc".equals(childNode.getNodeName())) { //$NON-NLS-1$
                parseNode(childNode);
            }
        }
    }

    private void parseNode(Node childNode) {
        Properties properties = parseAttributes(childNode);
        String key = properties.getProperty("key");
        String category = properties.getProperty("category", "");
        String tag = properties.getProperty("tag", "");
        String value = childNode.getTextContent();
        String[] lines = StringUtils.split(value, '\n');
        List<String> commentLines = new ArrayList<>();
        Arrays.asList(lines).forEach(str -> {
            String trim = StringUtils.trim(str);
            if (StringUtils.isNotEmpty(trim)) {
                commentLines.add(trim);
            }
        });
        String computedKey = key.concat(category).concat(tag);
        comments.putIfAbsent(computedKey,
                CommentInner.builder().category(category).tag(tag).lines(commentLines).build());
    }

    protected Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap nnm = node.getAttributes();
        for (int i = 0; i < nnm.getLength(); i++) {
            Node attribute = nnm.item(i);
            String value = attribute.getNodeValue();
            attributes.put(attribute.getNodeName(), value);
        }

        return attributes;
    }

    public List<String> getComments(String key) {
        return getComments(key, "", "", Collections.emptyMap());
    }

    public List<String> getComments(String key, String category) {
        return getComments(key, category, "", Collections.emptyMap());
    }

    public List<String> getComments(String key, String category, String tag) {
        return getComments(key, category, tag, Collections.emptyMap());
    }

    public List<String> getComments(String key, String category, Map<String, Object> variables) {
        return getComments(key, category, "", variables);
    }

    public List<String> getComments(String key, String category, String tag, Map<String, Object> variables) {
        String computedKey = StringUtils.defaultString(key, "").concat(StringUtils.defaultString(category, ""))
                .concat(StringUtils.defaultString(tag, ""));

        if (comments.containsKey(computedKey)) {
            CommentInner inner = comments.get(computedKey);
            List<String> result = new ArrayList<>();
            inner.lines.forEach(line -> result.add(replace(line, variables)));
            return result;
        }
        return Collections.emptyList();
    }

    private String replace(String target, Map<String, Object> variables) {
        if (variables == null || variables.size() == 0) {
            return target;
        }
        String[] searchStrings = new String[variables.size()];
        String[] replaceStrings = new String[variables.size()];
        AtomicInteger index = new AtomicInteger(0);
        variables.forEach((key, value) -> {
            searchStrings[index.get()] = "{" + key + "}";
            replaceStrings[index.get()] = String.valueOf(value);
            index.getAndIncrement();
        });
        return StringUtils.replaceEachRepeatedly(target, searchStrings, replaceStrings);
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.join(CommentHelper.INSTANCE.getComments("test"), "\n"));
        ;
    }

    @Data
    @Builder(builderClassName = "Builder")
    public static class CommentInner {

        private String key;

        private String category;

        private String tag;

        private List<String> lines;

    }

}
