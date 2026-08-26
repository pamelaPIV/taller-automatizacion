package com.iplacex.calculadora;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * Runner que conecta JUnit 5 con Cucumber.
 * Le indica a Maven dónde buscar los archivos .feature (features)
 * y dónde están los Step Definitions (glue).
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.iplacex.calculadora.steps")
public class RunCucumberTest {
}