package fr.tycabris.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, fr.tycabris.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, fr.tycabris.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, fr.tycabris.domain.User.class.getName());
            createCache(cm, fr.tycabris.domain.Authority.class.getName());
            createCache(cm, fr.tycabris.domain.User.class.getName() + ".authorities");
            createCache(cm, fr.tycabris.domain.PersistentToken.class.getName());
            createCache(cm, fr.tycabris.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName());
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".parcChevres");
            createCache(cm, fr.tycabris.domain.Parc.class.getName());
            createCache(cm, fr.tycabris.domain.Parc.class.getName() + ".parcChevres");
            createCache(cm, fr.tycabris.domain.ParcChevre.class.getName());
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".evenementChevres");
            createCache(cm, fr.tycabris.domain.Evenement.class.getName());
            createCache(cm, fr.tycabris.domain.Evenement.class.getName() + ".evenementChevres");
            createCache(cm, fr.tycabris.domain.Poids.class.getName());
            createCache(cm, fr.tycabris.domain.Poids.class.getName() + ".chevres");
            createCache(cm, fr.tycabris.domain.EvenementChevre.class.getName());
            createCache(cm, fr.tycabris.domain.Taille.class.getName());
            createCache(cm, fr.tycabris.domain.Taille.class.getName() + ".chevres");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".poids");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".tailles");
            createCache(cm, fr.tycabris.domain.Evenement.class.getName() + ".suivants");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".peres");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".meres");
            createCache(cm, fr.tycabris.domain.Chevre.class.getName() + ".chevres");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
