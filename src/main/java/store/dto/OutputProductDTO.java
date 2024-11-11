package store.dto;

public record OutputProductDTO(
        String name,
        Integer price,
        Integer quantity,
        String promotion
) {
}
