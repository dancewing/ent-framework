import type { AppRouteModule } from 'fe-ent-core/lib/router/types';

import { default as LAYOUT } from 'fe-ent-core/lib/layouts/default';

const routes: AppRouteModule = {
  path: '${routerPrefixPath}',
  name: 'BaseRouteData',
  component: LAYOUT,
  redirect: '/basic/config',
  meta: {
    icon: 'simple-icons:about-dot-me',
    title: '教学系统',
    orderNo: 20,
  },
  children: [
 <#list models as model>
    {
      path: '${model.camelModelName}',
      name: '${model.name}Management',
      component: () => import('${projectRootAlias}${viewPath}/${model.camelModelName}/${model.camelModelName}.list.vue'),
      meta: {
        title: '${model.description}',
        icon: 'simple-icons:about-dot-me',
      },
    },
</#list>
  ],
};

export default routes;
