package com.example.notification;

import com.likelion.domain.notification.usecase.SaveFcmTokenUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class FcmService_Factory implements Factory<FcmService> {
  private final Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider;

  public FcmService_Factory(Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider) {
    this.saveFcmTokenUseCaseProvider = saveFcmTokenUseCaseProvider;
  }

  @Override
  public FcmService get() {
    FcmService instance = newInstance();
    FcmService_MembersInjector.injectSaveFcmTokenUseCase(instance, saveFcmTokenUseCaseProvider.get());
    return instance;
  }

  public static FcmService_Factory create(
      Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider) {
    return new FcmService_Factory(saveFcmTokenUseCaseProvider);
  }

  public static FcmService newInstance() {
    return new FcmService();
  }
}
