package ru.fau.nia.dto;

import ru.fau.nia.dto.item.Item;
import ru.fau.nia.entity.DeclarationInfo;
import ru.fau.nia.entity.DictBusinessLineType;

public interface DeclarationVisitor {
    void visit(Declaration declaration);

    void visit(DeclarationInfo declarationInfo);

    void visit(AccreditationLine accreditationLine);

    void visit(Address address);

    void visit(AccreditationAreas accreditationAreas);

    void visit(DictBusinessLineType businessLineType);

    void visit(AccreditationItem accreditationItem);

    void visit(Item item);
}
