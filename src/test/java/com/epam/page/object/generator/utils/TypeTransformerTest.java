package com.epam.page.object.generator.utils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.epam.page.object.generator.model.RawSearchRule;
import com.epam.page.object.generator.model.Selector;
import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.SearchRule;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TypeTransformerTest {

    private TypeTransformer typeTransformer;

    @Mock
    private RawSearchRule rawSearchRule;

    private List<RawSearchRule> rawSearchRules = Lists.newArrayList(rawSearchRule);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        typeTransformer = new TypeTransformer();
    }

    @Test
    public void transform_SuccessWithCommonSearchRule() throws Exception {
        List<SearchRule> searchRules = typeTransformer.transform(rawSearchRules);

        assertEquals(Lists.newArrayList(new CommonSearchRule("value", SearchRuleType.BUTTON,
            new Selector("xpath", "//input[@type='submit']"))), searchRules);
    }

}