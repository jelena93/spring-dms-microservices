package gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
public class ViewConfigurationBean {

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        return tilesViewResolver;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tiles = new TilesConfigurer();
        tiles.setDefinitions(new String[]{"/ui/tiles/tiles.xml"});
        return tiles;

    }
}
