package com.milelu.freemark.handler;

import freemarker.template.Configuration;

import java.util.Map;

public interface DirectiveInterceptor {

  Configuration injectionGlobalVariable(Map<String,Object> params);

  Configuration injectionShareVariable(Map<String,Object> params);
}
