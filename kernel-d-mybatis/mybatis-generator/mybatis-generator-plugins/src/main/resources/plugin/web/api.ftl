import { ${modelName}Request, ${modelName}Response } from '${projectRootAlias}${modelPath}/${camelModelName}';
import { defHttp } from 'fe-ent-core/lib/utils/http/axios';

export const Create${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<${modelName}Response>({ url: '${apiPrefix?default('')}/${camelModelName}/create', data });

export const BatchCreate${modelName} = (data?: ${modelName}Request[]) =>
  defHttp.post<number>({ url: '${apiPrefix?default('')}/${camelModelName}/batch-create', data });

export const ListQuery${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<${modelName}Response[]>({ url: '${apiPrefix?default('')}/${camelModelName}/list', data });

export const PageQuery${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<number>({ url: '${apiPrefix?default('')}/${camelModelName}/page', data });

export const Update${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<${modelName}Response>({ url: '${apiPrefix?default('')}/${camelModelName}/update', data });

export const Delete${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<${modelName}Response>({ url: '${apiPrefix?default('')}/${camelModelName}/delete', data });

export const BatchDelete${modelName} = (data?: ${modelName}Request) =>
  defHttp.post<${modelName}Response>({ url: '${apiPrefix?default('')}/${camelModelName}/batch-delete', data });
