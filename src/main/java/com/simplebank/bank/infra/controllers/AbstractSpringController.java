package com.simplebank.bank.infra.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class AbstractSpringController
{
  protected final ObjectMapper mapper;
  protected final ObjectNode body;

  protected AbstractSpringController()
  {
    this.mapper = new ObjectMapper();
    this.body = mapper.createObjectNode();
  }
}
