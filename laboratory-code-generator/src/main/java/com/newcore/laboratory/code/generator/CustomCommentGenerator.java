package com.newcore.laboratory.code.generator;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义MyBatis生成器注释生成器
 * @author zhouchaowei
 */
public class CustomCommentGenerator implements CommentGenerator {

    private static final String TO_STRING_METHOD_NAME = "toString";
    private static final String COUNT_BY_EXAMPLE_METHOD_NAME = "countByExample";
    private static final String DELETE_BY_EXAMPLE_METHOD_NAME = "deleteByExample";
    private static final String DELETE_BY_PRIMARY_KEY_METHOD_NAME = "deleteByPrimaryKey";
    private static final String INSERT_METHOD_NAME = "insert";
    private static final String INSERT_SELECTIVE_METHOD_NAME = "insertSelective";
    private static final String SELECT_BY_EXAMPLE_METHOD_NAME = "selectByExample";
    private static final String SELECT_BY_PRIMARY_KEY_METHOD_NAME = "selectByPrimaryKey";
    private static final String UPDATE_BY_EXAMPLE_SELECTIVE_METHOD_NAME = "updateByExampleSelective";
    private static final String UPDATE_BY_EXAMPLE_METHOD_NAME = "updateByExample";
    private static final String UPDATE_BY_PRIMARY_KEY_SELECTIVE_METHOD_NAME = "updateByPrimaryKeySelective";
    private static final String UPDATE_BY_PRIMARY_KEY_METHOD_NAME = "updateByPrimaryKey";

    private final Properties properties;
    private final Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;
    private final String currentDateStr;

    public CustomCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
        currentDateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * 添加属性注释
     * @param field 属性对象
     * @param introspectedTable 表对象
     * @param introspectedColumn 字段对象
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        //TODO
    }

    /**
     * 添加模型PO类的注释
     * @param modelClass 模型类对象
     * @param introspectedTable 表对象
     */
    @Override
    public void addModelClassComment(TopLevelClass modelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        modelClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        modelClass.addJavaDocLine(sb.toString());
        sb.append(" * ");
        addJavadocTag(modelClass, false);
        modelClass.addJavaDocLine(" */");
    }

    /**
     *
     * @param innerClass
     *            the inner class
     * @param introspectedTable
     *            the introspected table
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(" ");
        sb.append(getDateString());
        innerClass.addJavaDocLine(sb.toString());
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean flag) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerClass.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @author ");
        sb.append(systemPro.getProperty("user.name"));
        sb.append(" ");
        sb.append(currentDateStr);
        addJavadocTag(innerClass,false);
        innerClass.addJavaDocLine(" */");
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        innerEnum.addJavaDocLine("/** ");
        addJavadocTag(innerEnum, false);
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());
        innerEnum.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * 获取");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        addJavadocTag(method, false);
        sb.append(" ");
        sb.setLength(0);
        sb.append(" * @return ");
        sb.append(" ");
        method.addJavaDocLine(sb.toString());
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * 设置");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());

        Parameter parm = method.getParameters().get(0);
        sb.setLength(0);
        sb.append(" * @param ");
        sb.append(parm.getName());
        sb.append(" ");
        sb.append(introspectedColumn.getRemarks());
        method.addJavaDocLine(sb.toString());
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        addMethodJavaDoc(method);
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        return;
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        return;
    }

    @Override
    public void addRootComment(XmlElement xmlElement) {
        return;
    }

    /**
     * 生成日期方法
     * @return
     */
    protected String getDateString() {
        String result = null;
        if (!suppressDate) {
            result = currentDateStr;
        }
        return result;
    }

    /**
     * 在JavaDoc注释中添加日期
     * @param javaElement
     * @param markAsDoNotDelete
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *");
        StringBuilder sb = new StringBuilder();
        sb.append(" * @date");
        if (markAsDoNotDelete) {
            sb.append(" ");
        }
        String s = getDateString();
        if (s != null) {
            sb.append(" ");
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * 根据方法名生成固定的方法名JavaDoc注释
     * @param method 方法名
     */
    protected void addMethodJavaDoc(Method method){
        StringBuilder sb = new StringBuilder();
        switch (method.getName()){
            case TO_STRING_METHOD_NAME : sb.append(" * " + TO_STRING_METHOD_NAME + " 方法"); break;
            case COUNT_BY_EXAMPLE_METHOD_NAME : sb.append(" * " + COUNT_BY_EXAMPLE_METHOD_NAME + " 方法"); break;
            case DELETE_BY_EXAMPLE_METHOD_NAME : sb.append(" * " + DELETE_BY_EXAMPLE_METHOD_NAME + " 方法"); break;
            case DELETE_BY_PRIMARY_KEY_METHOD_NAME : sb.append(" * " + DELETE_BY_PRIMARY_KEY_METHOD_NAME + " 方法"); break;
            case INSERT_METHOD_NAME : sb.append(" * " + INSERT_METHOD_NAME + " 方法"); break;
            case INSERT_SELECTIVE_METHOD_NAME : sb.append(" * " + INSERT_SELECTIVE_METHOD_NAME + " 方法"); break;
            case SELECT_BY_EXAMPLE_METHOD_NAME : sb.append(" * " + SELECT_BY_EXAMPLE_METHOD_NAME + " 方法"); break;
            case SELECT_BY_PRIMARY_KEY_METHOD_NAME : sb.append(" * " + SELECT_BY_PRIMARY_KEY_METHOD_NAME + " 方法"); break;
            case UPDATE_BY_EXAMPLE_SELECTIVE_METHOD_NAME : sb.append(" * " + UPDATE_BY_EXAMPLE_SELECTIVE_METHOD_NAME + " 方法"); break;
            case UPDATE_BY_EXAMPLE_METHOD_NAME : sb.append(" * " + UPDATE_BY_EXAMPLE_METHOD_NAME + " 方法"); break;
            case UPDATE_BY_PRIMARY_KEY_SELECTIVE_METHOD_NAME : sb.append(" * " + UPDATE_BY_PRIMARY_KEY_SELECTIVE_METHOD_NAME + " 方法"); break;
            case UPDATE_BY_PRIMARY_KEY_METHOD_NAME : sb.append(" * " + UPDATE_BY_PRIMARY_KEY_METHOD_NAME + " 方法"); break;
            default : sb.append(" * " + method.getName() + " 方法"); break;
        }
        method.addJavaDocLine(sb.toString());
    }
}
