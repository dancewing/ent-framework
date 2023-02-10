import { BasicColumn } from 'fe-ent-core/lib/components/table';
import { FormSchema } from 'fe-ent-core/lib/components/form';
<#list enumFields as field>
import { ${field.javaType.shortName}_All } from '${field.javaType.packagePath}';
</#list>
<#list relationFields as field>
import { ${field.javaType.shortName}List } from '${projectRootAlias}${apiTargetPackage}/${field.javaType.fileName}';
</#list>

export const columns: BasicColumn[] = [
<#list listFields as field>
<#if field.relationField && field.relationOne>
  {
    title: '${field.description}',
    dataIndex: '${field.name}',
    width: 110,
    customRender: ({ text, index }: { text: any; index: number }) => {
      console.log(JSON.stringify(text));
      const name = text?.${field.relation.displayField};
      const obj: any = {
        children: name,
        attrs: {},
      };
      return obj;
    },
  },
<#else>
  {
    title: '${field.description}',
    dataIndex: '${field.name}',
    width: 120,
  },
</#if>
</#list>
  {
    title: '创建人',
    dataIndex: 'createUserName',
    width: 120,
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    width: 120,
  },
  {
    title: '更新人',
    dataIndex: 'updateUserName',
    width: 120,
  },
  {
    title: '更新时间',
    dataIndex: 'updateTime',
    width: 120,
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'account',
    label: '用户名',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'nickname',
    label: '昵称',
    component: 'Input',
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
<#list inputFields as field>
  <#assign inputType="Input"/>
  <#if field.fieldType == 'number'>
    <#assign inputType="InputNumber"/>
  <#elseif field.fieldType == 'date'>
    <#assign inputType="DatePicker"/>
  <#elseif field.fieldType == 'time'>
    <#assign inputType="TimePicker"/>
  <#elseif field.fieldType == 'date-time'>
    <#assign inputType="DatePicker"/>        
  <#elseif field.fieldType == 'boolean'>
    <#assign inputType="Switch"/>
  <#elseif field.fieldType == 'enum'>
    <#assign inputType="Select"/>         
  <#elseif field.fieldType == 'relation'>
    <#assign inputType="ApiSelect"/>       
  </#if>
  {
    field: '${field.name}',
    label: '${field.description}',
    component: '${inputType}',
    required: ${field.required?c},
<#if field.fieldType == 'enum'>
    componentProps: {
      options: ${field.javaType.shortName}_All,
    },
<#elseif field.fieldType == 'relation'>
    componentProps: {
      // more details see /src/components/Form/src/components/ApiSelect.vue
      api: ${field.targetRelation.bindField.type.shortName}List,
      resultField: 'items',
      // use name as label
      labelField: '${field.targetRelation.displayField}',
      // use id as value
      valueField: '${field.targetRelation.targetColumn.javaProperty}',
      // not request untill to select
      immediate: true,
    },
</#if>    
  },
</#list>
];
