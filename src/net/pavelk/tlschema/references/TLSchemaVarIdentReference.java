package net.pavelk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.TLSchemaDeclaration;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaUcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaVarIdent;
import net.pavelk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TLSchemaVarIdentReference extends TLSchemaNamedElementReferenceBase {
    private final TLSchemaDeclaration declaration;

    public TLSchemaVarIdentReference(@NotNull TLSchemaVarIdent element) {
        super(element, TextRange.create(0, element.getTextLength()));
        declaration = element.getDeclaration();
    }

    public TLSchemaVarIdentReference(@NotNull TLSchemaLcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        declaration = element.getDeclaration();
    }

    public TLSchemaVarIdentReference(@NotNull TLSchemaUcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        declaration = element.getDeclaration();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<TLSchemaVarIdent> idents = TLSchemaSearchUtils.findVarIdents(declaration, myName);
        List<ResolveResult> results = new ArrayList<>();
        for (TLSchemaVarIdent ident : idents) {
            results.add(new PsiElementResolveResult(ident));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        List<TLSchemaVarIdent> idents = TLSchemaSearchUtils.findVarIdents(declaration);
        List<LookupElement> variants = new ArrayList<>();
        for (final TLSchemaVarIdent ident : idents) {
            variants.add(LookupElementBuilder.create(ident).
                    withIcon(TLSchemaIcons.FILE).
                    withTypeText(ident.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}