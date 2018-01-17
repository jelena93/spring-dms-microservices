package process.rabbit;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cloud.stream.annotation.StreamListener;

@org.springframework.stereotype.Service
public class Service {
//    @StreamListener(ProductInputChannel.PRODUCT_DELETED_INPUT)
//    public void handleDeletedProduct(Product product) {
//        shoppingListRepository.deleteProductsById(product.getId());
//    }
//
//    @StreamListener(ProductInputChannel.PRODUCT_UPDATED_INPUT)
//    public void handleUpdatedProduct(Product product) {
//        cacheManager.getCache(CachingConfiguration.PRODUCTS_CACHE).put(product.getId(), product);
//    }
}
