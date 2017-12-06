package com.epam.page.object.generator.adapter;

import com.epam.page.object.generator.model.searchRules.CommonSearchRule;
import com.epam.page.object.generator.model.searchRules.ComplexSearchRule;
import com.epam.page.object.generator.model.searchRules.FormSearchRule;

public interface JavaFieldVisitor {

    IJavaField visit(CommonSearchRule searchRule);

    IJavaField visit(ComplexSearchRule searchRule);

    IJavaField visit(FormSearchRule searchRule);
}
