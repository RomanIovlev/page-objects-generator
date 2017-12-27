
[![Build Status](https://travis-ci.org/TAI-EPAM/page-objects-generator.svg?branch=master)](https://travis-ci.org/TAI-EPAM/page-objects-generator)
[![codecov](https://codecov.io/gh/TAI-EPAM/page-objects-generator/branch/master/graph/badge.svg)](https://codecov.io/gh/TAI-EPAM/page-objects-generator)
[![Maintainability](https://api.codeclimate.com/v1/badges/e527808dc8bd7806783f/maintainability)](https://codeclimate.com/github/TAI-EPAM/page-objects-generator/maintainability)
# page-objects-generator
Page Object Generator allows generate .java source files for JDI UI Test Automation Framework. 
You only need to create json file with SearchRules, run application and finally get .java source files. 

1. [Synopsis](#synopsis)
2. [Getting Started](#getting-started)
	- [Download application](#download-application)
	- [Example Use](#example-use)
	- [Example Tests](#example-tests)
		- [Simple Tests](#simple-tests)
		- [EndToEnd Tests](#endtoend-tests)	
3. [SearchRule](#searchrule)
4. [JSON structure](#json-structure)
	- [Common SearchRule](#common-searchrule)
	- [Complex SearchRule](#common-searchrule)
	- [Form and Section SearchRules](#form-and-section-searchrules)
5. [SearchRuleGroups](#searchrulegroups)
5. [Custom elements](#custom-elements)
	- [Creating SearchRule](#creating-searchrule)
	- [Creating SearchRuleBuilder](#creating-searchrulebuilder)
	- [How to add a new type of element in an existing WebElementGroup](#how-to-add-a-new-type-of-element-in-an-existing-webelementgroup)
	- [How to add a new WebElementGroup](#how-to-add-a-new-webelementgroup)
	- [Creating Validator](#creating-validator)
6. [What technologies we used](#what-technologies-we-used)
## Synopsis
In general, POG is used for JDI-framework for testing web sites and definitions different types of elements on web pages. For instance, button, dropdown list and etc.
POG is needed for generating .java source files, which are based on JSON file with rules and list of URL.
By using POG tester-engineer can generate java file for JDI-framework and can do work faster.

Structure of project:
1) First step is user has to write JSON file by rules;
2) After that program creates list of `SearchRules` which are based on JSON file;
3) Then JSON Schema Validator checks list of `SearchRules` on validness: as result throw exceptions if file has some problem, otherwise all list of `SearchRules` goes further;
4) After successful validation `TypeTransformer` class splits every `SearchRule` on three types: `CommonSearchRule`, `ComplexSearchRule`, `FormSearchRule`;
5) Then every `SearchRule` is checked by business logic on accordance. This is another place, where we can see exception;
6) If we have list of `SearchRules`, program takes list of URL and `WebPageBuilder` and creates `WebPage` or generate exception;
7) Then program creates list of `WebElementGroup` by checking accordance `WebPage` and `SearchRule`;
8) `JavaClassBuilder` decides which type of java file has to be build and puts them in `JavaClass`;
9) After all these manipulations program gives command for `JavaWriter` and .java source files are written in file.
## Getting Started
### Download application
For downloading application you can use console and clone repository:
```text
git clone https://github.com/TAI-EPAM/page-objects-generator.git
```
### Example Use
All that you need to do is create `PageObjectGenerator` instance by the `PageObjectGeneratorFactory` and call `generatePageObjects` method.
```java
package com.epam.page.object.generator.example;

import com.epam.page.object.generator.PageObjectGeneratorFactory;
import com.epam.page.object.generator.PageObjectsGenerator;
import java.io.IOException;
import java.util.Collections;

public class ExampleTest {

    public static void main(String[] args) throws IOException {
        String outputDir = "src/test/resources/";
        String packageName = "test";

        PageObjectsGenerator pog = PageObjectGeneratorFactory
            .getPageObjectGenerator(packageName, "/groups.json", false);

        pog.generatePageObjects("/example/calculate.json", outputDir,
            Collections.singletonList("/example/example.html"));
    }
}
```
When you create `PageObjectGenerator` you need to specify three parameters:
 - `packageName` - name of the package where will be must placed all generated .java source files.
 - `propertyFile` - the path to the property file which contains all information about groups of SearchRule.
 - `onlineVersion` - boolean parameter which specify what kind of PageObjectGenerator you will use.<br/>
 If `onlineVersion` will be true - PageObjectGenerator will work with urls and web sites which exist in the Internet.<br/>
 If `onlineVersion` will be false - PageObjectGenerator will work with local .html files.
 
In `generatePageObjects` method you need to specify three parameters:
 - `jsonPath` - path to the input json file, which must start from folder resources.
 - `outputDir` - path to the folder where it is needed to be generated .java source files.
 - `urls/paths` - list of web site urls or list of string paths to the local .html files. 
### Example Tests
#### Simple Tests
You can find "example" folder in tests, which contains [ExampleTest](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/test/java/com/epam/page/object/generator/example/ExampleTest.java) file.
Each test need to run in isolation from others, because folder with generating .java source files are cleared before each test. 
In [ExampleTest](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/test/java/com/epam/page/object/generator/example/ExampleTest.java) 
you can change `outputDir` where will be generate .java source files and `packageName` from which will be started structure of the project.
```
private String outputDir = "src/test/resources/";
private String packageName = "test";
```
At the end of each test you can find generating .java source file in folder which located by the path: `outputDir` + `packageName`. <br/>
For example run `exampleTestCommonElement`. We can see our .java source files by the following path: "src/test/resources/test".<br/>
<p align="center"><img src ="https://user-images.githubusercontent.com/13944502/34299793-e66f4ec8-e735-11e7-91b3-48a7d0787e66.png" /></p>

#### EndToEnd Tests
## SearchRule
`SearchRule` is set of rules using for searching web components on web pages. User could set `SearchRules`
 in JSON files in special structure which you could see in [JSON structure](#json-structure)
section. There are 3 different types of `SearchRule` now: `CommonSearchRule`, `ComplexSearchRule` and `FormSearchRule`.
Respectively they all have their own JSON structure.
## JSON structure
Page object generator uses JSON files to form search rules for getting elements from web pages.
There are three types of JSON structure that can be processed by POG, for each of search rule type:
### Common SearchRule
```json
{
  "elements": [
    {
      "type": "button",
      "uniqueness": "value",
      "selector": {
        "type": "xpath",
        "value": "//input[@type='submit']"
      }
    }
  ]
}
```
As seen on given example, JSON for common element consist of this parameters:
1. `type` - type of web element to search, from list SearchRuleType class.
2. `uniqueness` - attribute of web element that must be unique on that page.
3. `selector` have two parameters:

   * `type` - the type of selector, `xpath` or `css` by which we search element on page
   * `value` - the value that element must correspond to search result by described type.
   
### Complex SearchRule
Complex elements, such as dropdown menus, containing list of simple elements processed by
`ComplexSearchRule` :
```json
{
  "elements": [
    {
      "type": "dropdown",
      "innerSearchRules": [
        {
          "title": "root",
          "uniqueness": "text",
          "selector":{
            "type": "xpath",
            "value": "//button[@class='dropbtn']"
          }
        }
      ]
    }
  ]
}
```
1. `type` - type of web element to search, from list SearchRuleType class.
2. `innerSearchRule` - list of search rules for elements containing in complex element. Inner 
search rules have this parameters:
   * `title` - contains name that will be used to build annotation for found element. 
   It is required to have one inner search element with this field have value `"root"`.
   * `uniquueness` - attribute of web element that must be unique on that page.
   * `selector` have two parameters:
   
      * `type` - the type of selector, `xpath` or `css` by which we search element on page
      * `value` - the value that element must correspond to search result by described type.
### Form and Section SearchRules
Form with section (such as login form) requires specific kind of json. 
```json
{
  "elements": [
    {
      "type": "form",
      "section": "HtmlForm",
      "selector": {
        "type": "css",
        "value": ".w3-example form"
      },
      "innerSearchRules": [
        {
          "type": "textfield",
          "uniqueness": "name",
          "selector": {
            "type": "css",
            "value": "input[type=text]"
          }
        },
        {
          "type": "button",
          "uniqueness": "value",
          "selector": {
            "type": "css",
            "value": "input[type=submit]"
          }
        }
      ]
    }
  ]
}
```
1. `type` -  must have value `"form"`
2. `section` - describes type of form.
3. `selector` have two parameters:
      
      * `type` - the type of selector, `xpath` or `css` by which we search element on page
      * `value` - the value that element must correspond to search result by described type.
4. `innerSearchRule` - list of search rules for elements containing in complex element. Inner 
search rules have this parameters:
   * `type` - contains name that will be used to build annotation for found element. 
   It is required to have one inner search element with this field have value `"root"`.
   * `uniquueness` - attribute of web element that must be unique on that page.
   * `selector` have two parameters:
 
      * `type` - the type of selector, `xpath` or `css` by which we search element on page
      * `value` - the value that element must correspond to search result by described type.
## SearchRuleGroups
By default the POG supported three groups of elements:
1. `CommonSearchRule`  group - simple single elements without internal contents
2. `ComplexSearchRule` group - complex elements with internal contents
3. `FormSearchRule`  group - forms that include internal elements.

You can find all existing groups in `groups.json` file which places in the resources folder. 
In this file you can see `typeGroups` array which contains all accessible SearchRuleGroups.

Each group has three parameters: 
1. `name` - name of the group, which will be used in the java code for getting information about this group.
2. `searchRuleTypes` - list of element types which contains in this group.
3. `schema` - name of json schema which will be validate elements that suitable for this group. (the path is starting from the resources folder).

For example consider FormSearchRule group:
```
{
  "name": "formSearchRule",
  "searchRuleTypes": [
    "form",
    "section"
  ],
  "schema": "/schema/formSearchRule_schema.json"
}
```
We can see that this group has "formSearchRule" name and only two types of SearchRules (form and section) suit for this group.
You can add your custom group. About this you can read in the [following section](#how-to-add-a-new-webelementgroup).
## Custom elements
In this section we will look at examples how to add your own custom elements.
### Creating SearchRule
To create a new `SearchRule`, you should implement the [SearchRule](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/searchrule/SearchRule.java)
interface which extends from [Validatable](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/searchrule/Validatable.java)
interface.

If you don't follow any of given `SearchRules` (e.g. you want to use another unique element), then 
you can create your own `SearchRule`. Consider the creation a new `SearchRule` on the following example.

`MySearchRule` :
```java
public class MySearchRule implements SearchRule {
    private String uniqueness;
    private SearchRuleType type;
    private Selector selector;
    private ClassAndAnnotationPair classAndAnnotation;
    private XpathToCssTransformer transformer;
    private SelectorUtils selectorUtils;

    private List<ValidationResult> validationResults = new ArrayList<>();

    public MySearchRule(String uniqueness, SearchRuleType type, Selector selector,
                            ClassAndAnnotationPair classAndAnnotation,
                            XpathToCssTransformer transformer,
                            SelectorUtils selectorUtils) {
        this.uniqueness = uniqueness;
        this.type = type;
        this.selector = selector;
        this.classAndAnnotation = classAndAnnotation;
        this.transformer = transformer;
        this.selectorUtils = selectorUtils;
    }

    public String getUniqueness() {
        return uniqueness;
    }

    public SearchRuleType getType() {
        return type;
    }

    private String getRequiredValue(Element element) {
        return uniqueness.equals("MyElement")
            ? element.text()
            : element.attr(uniqueness);
    }

    public ClassAndAnnotationPair getClassAndAnnotation() {
        return classAndAnnotation;
    }

    public Selector getTransformedSelector() {
        if (!uniqueness.equalsIgnoreCase("MyElement") && selector.isXpath()) {
            try {
                return transformer.getCssSelector(selector);
            } catch (XpathToCssTransformerException e) {
                e.printStackTrace();
            }
        }
        return selector;
    }

    @Override
    public Selector getSelector() {
        return selector;
    }

    @Override
    public List<WebElement> getWebElements(Elements elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (Element element : elements) {
            webElements.add(new CommonWebElement(element, getRequiredValue(element)));
        }
        return webElements;
    }

    @Override
    public void fillWebElementGroup(List<WebElementGroup> webElementGroups, Elements elements) {
        webElementGroups.add(new CommonWebElementGroup(this, getWebElements(elements),
            selectorUtils));
    }

    @Override
    public void accept(ValidatorVisitor validatorVisitor) {
        ValidationResult visit = validatorVisitor.visit(this);
        validationResults.add(visit);
    }

    @Override
    public List<ValidationResult> getValidationResults() {
        return validationResults;
    }

    @Override
    public boolean isValid() {
        return validationResults.stream().allMatch(ValidationResult::isValid);
    }

    @Override
    public boolean isInvalid() {
        return validationResults.stream()
            .anyMatch(validationResultNew -> !validationResultNew.isValid());
    }
}
```
Also for the implementation of full functionality necessary to create new `SearchRuleBuilder` (for building custom `SearchRule`)
and `WebElementGroup` (which contains `SearchRule` and `Elements` found by them). About creation these elements you can read below.
### Creating SearchRuleBuilder
`SearchRuleBuilder` describes how to create a typed [SearchRule](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/searchrule/SearchRule.java)
from an existing [RawSearchRule](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/RawSearchRule.java)
using supported types of elements. (`SearchRule` is a validated `RawSearchRule`)

To create a new `SearchRuleBuilder`, you should implement the [SearchRuleBuilder](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/builder/searchrule/SearchRuleBuilder.java)
interface. Consider the creation a new `SearchRuleBuilder` on the following example.

`MySearchRuleBuilder` :
```java
public class MySearchRuleBuilder implements SearchRuleBuilder {

    @Override
    public SearchRule buildSearchRule(RawSearchRule rawSearchRule,
                                      SupportedTypesContainer typesContainer,
                                      XpathToCssTransformer transformer,
                                      SelectorUtils selectorUtils,
                                      SearchRuleExtractor searchRuleExtractor) {
        String uniqueness = rawSearchRule.getValue("uniqueness");
        SearchRuleType type = rawSearchRule.getType();
        Selector selector = rawSearchRule.getSelector();
        ClassAndAnnotationPair classAndAnnotation = typesContainer.getSupportedTypesMap()
                                                                  .get(type.getName());
        
        SearchRule mySearchRule = new MySearchRule(uniqueness, type, selector,
            classAndAnnotation, transformer, selectorUtils);

        return mySearchRule;
    }
}
``` 
### How to add a new type of element in an existing WebElementGroup
1)Add a new type of element in the properties file.

You need to add a new type of element in `groups.json` file. (if you don't read about this file, you can do it by the [following link](#searchrulegroups)).
For adding a new type of element you need to choose one of existing group which suitable for this element and add a new type in `searchRuleTypes` array of this group.

For example, adding CustomButton type in ComplexSearchRule:
```
{
  "name": "complexSearchRule",
  "searchRuleTypes": [
    "table",
    "combobox",
    "dropdown",
    "droplist",
    "menu",
    "custombutton"
  ],
  "schema": "/schema/complexSearchRule_schema.json"
}
```
2)Add a new type of element in [SearchRuleType](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/util/SearchRuleType.java) enum.

If you open SearchRuleType enum, you can see list of element types which is supported by the page object generator. If you add a new type of element, you must add him in this enum.

Enum consists of:
- `enum value` - used in the java code for identify type of the element;
- `string name` - used in json file when we write "type" for SearchRule.

In the previous example we have added a CustomButton type and it's new, so it is necessary to add in [SearchRuleType](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/util/SearchRuleType.java) enum:
 
```
//added new type
CUSTOMBUTTON("custombutton")
```
3)Add a new type of element in SupportedTypesMap from [SupportedTypesContainer](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/container/SupportedTypesContainer.java) class.

The last thing you need to do is add a new type of element in SupportedTypesMap from [SupportedTypesContainer](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/container/SupportedTypesContainer.java) class. This map contains:
- `key` - type of supported elements;
- `value` - pairs which consists of class and annotation that will be used for generating this type of elements.
 
For our example we need to add the following code in [SupportedTypesContainer](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/container/SupportedTypesContainer.java):
```
//added new type
supportedTypesMap.put(SearchRuleType.CUSTOMBUTTON.getName(),
    new ClassAndAnnotationPair(CustomButton.class, FindBy.class));
```
### How to add a new WebElementGroup
All supported groups of web-elements are described in the file in `groups.json`. 
About existing groups you can [read here](#searchrulegroups).

If you want to add only a new web-element you may add them to existing `WebElementGroup` [see here](#how-to-add-a-new-type-of-element-in-an-existing-webelementgroup).

For creating a new group, with a new web-element or elements you must follow these steps:
* Add a description of the new group into `typeGroups` section  of `groups.json` file. Specify:
	* `name` - name of the group, which will be used in the java code for getting information about this group.
	* `searchRuleTypes` - list of element types which contains in this group.
	* `schema` - name of json schema which will be validate elements that suitable for this group. (the path is starting from the resources folder).
```
{
  "typeGroups": [
    {
      "name": "newGroup",
      "searchRuleTypes": [
        "newElement1",
        "newElement2"
      ],
      "schema": "/schema/newGroup_schema.json"
    }
  ]
}
```
* Create `JSON schema` for a new group, this schema uses for json search rule validation by
[JSON Schema Validator](#JSON Schema Validator)
* Specify element types in the [SearchRuleType](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/util/SearchRuleType.java) class
    * Should add a new element types to enum
* Specify elements of group in the [SupportedTypesContainer](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/container/SupportedTypesContainer.java) class
    * Should add a new `ClassAndAnnotationPair` for all new web-elements in you group
* Create a new `SearchRule` for the group [see here](#creating-searchrule) if necessary
* Create a new web element class implementing [WebElement](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/webelement/WebElement.java) interface
* Create a new group class implementing [WebElementGroup](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/model/webgroup/WebElementGroup.java) interface and realize it methods
* Add a new overloaded `bulid` method in [WebElementGroupFieldBuilder](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/builder/WebElementGroupFieldBuilder.java) class as parameter
    uses created web-group element
    
```
public List<JavaField> build(NewGroup newGroup) {
    ...
}
```
### Creating Validator

To create new `Validator`, you should implement the [ValidatorVisitor](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/validator/ValidatorVisitor.java)
 interface. After success validation `visit` methods should return new instance of `ValidationResult`
 with `true` value of `isValid` field. If something went wrong than `ValidationResult` should be 
 with `false` value of `isValid` field and description of `reason` field.
 
Consider the creation a new `Validator` on the following example:

```java
public class TitleOfComplexElementValidator implements ValidatorVisitor {

    @Override
    public ValidationResult visit(ComplexSearchRule complexSearchRule) {

        Class<?> elementAnnotation = complexSearchRule.getClassAndAnnotation().getElementAnnotation();
        StringBuilder stringBuilder = new StringBuilder();
        Method[] declaredMethods = elementAnnotation.getDeclaredMethods();
        for (ComplexInnerSearchRule complexInnerSearchRule : complexSearchRule
            .getInnerSearchRules()) {
            if (Arrays.stream(declaredMethods)
                .noneMatch(method -> complexInnerSearchRule.getTitle().equals(method.getName()))) {
                stringBuilder.append("Title: ").append(complexInnerSearchRule.getTitle())
                    .append(" is not valid for Type: ").append(complexSearchRule.getType())
                    .append("\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return new ValidationResult(true, this + " passed!");
        }

        return new ValidationResult(false, stringBuilder.toString());
    }
}
```

To validate `SearchRule` objects with your custom `Validator`, you need to add it to 
[JsonValidators](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/validator/JsonValidators.java).

To validate `WebPage` objects with your custom `Validator`, you need to add it to 
[WebValidators](https://github.com/TAI-EPAM/page-objects-generator/blob/master/src/main/java/com/epam/page/object/generator/validator/WebValidators.java).
## What technologies we used
### Jsoup
Jsoup is a Java library for working with real-world HTML. It provides a very convenient API 
for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.

**Usage in project**

* Extract DOM from website and find elements by the rules with CSS selector.

*More information about Jsoup you can read [here](https://jsoup.org).*
### Xsoup
Xsoup is Java library which can parse DOM by using Xpath element. Xsoup parser based on Jsoup.

**Usage in project**

* Parse DOM and find elements by the rules with Xpath selector.

*More information about Xsoup you can read [here](https://github.com/code4craft/xsoup).*
### JavaPoet
JavaPoet is a Java API for generating `.java` source files.

**Usage in project**

* Generate `.java` source files.

*More information about JavaPoet you can read [here](https://github.com/square/javapoet).*
### JSON Schema Validator
JSON Schema is a declarative language for validating the format and structure of a JSON Object. 
It allows us to specify the number of special primitives to describe exactly what a valid JSON Object will look like.

**Usage in project**

* Validation json files.

*More information about JSON Schema Validator you can read 
[here](https://github.com/everit-org/json-schema).*
### Apache Commons Lang
Apache Commons Lang, a package of Java utility classes for the classes 
that are in java.lang's hierarchy, or are considered to be so standard as to 
justify existence in java.lang.

**Usage in project**

* String handling.

*More information about Apache Commons Lang you can read 
[here](https://commons.apache.org/proper/commons-lang).*
### Apache Commons Lang
Apache Commons Lang, a package of Java utility classes for the classes 
that are in java.lang's hierarchy, or are considered to be so standard as to 
justify existence in java.lang.

**Usage in project**

* String handling.

*More information about Apache Commons Lang you can read 
[here](https://commons.apache.org/proper/commons-lang).*
### Mockito
Mockito is a mocking framework that allows write tests with a clean and simple API. 
Tests are very readable and they produce clean verification errors.

**Usage in project**

* Use for unit testing.

*More information about Mockito you can read [here](http://site.mockito.org).*
### JUnit
JUnit is a test framework which uses annotations to identify methods that specify a test.

**Usage in project**

* Use for unit testing.

*More information about JUnit you can read [here](http://junit.org/junit5).*
### Log4j
Log4j is a reliable, fast and flexible logging framework (APIs) written in Java, 
which is distributed under the Apache Software License. 
Log4j is highly configurable through external configuration files at runtime. 
It views the logging process in terms of levels of priorities and offers mechanisms 
to direct logging information to a great variety of destinations, 
such as a database, file, console, etc.

**Usage in project**

* Add logs in the project.

*More information about JUnit you can read [here](https://logging.apache.org/log4j/2.x/index.html).*