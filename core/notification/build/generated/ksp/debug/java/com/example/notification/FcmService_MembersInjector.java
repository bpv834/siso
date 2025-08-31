package com.example.notification;

import com.likelion.domain.notification.usecase.SaveFcmTokenUseCase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class FcmService_MembersInjector implements MembersInjector<FcmService> {
  private final Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider;

  public FcmService_MembersInjector(Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider) {
    this.saveFcmTokenUseCaseProvider = saveFcmTokenUseCaseProvider;
  }

  public static MembersInjector<FcmService> create(
      Provider<SaveFcmTokenUseCase> saveFcmTokenUseCaseProvider) {
    return new FcmService_MembersInjector(saveFcmTokenUseCaseProvider);
  }

  @Override
  public void injectMembers(FcmService instance) {
    injectSaveFcmTokenUseCase(instance, saveFcmTokenUseCaseProvider.get());
  }

  @InjectedFieldSignature("com.example.notification.FcmService.saveFcmTokenUseCase")
  public static void injectSaveFcmTokenUseCase(FcmService instance,
      SaveFcmTokenUseCase saveFcmTokenUseCase) {
    instance.saveFcmTokenUseCase = saveFcmTokenUseCase;
  }
}
