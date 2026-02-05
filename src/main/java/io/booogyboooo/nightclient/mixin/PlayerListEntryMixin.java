package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import io.booogyboooo.nightclient.modules.render.CustomCapes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.SkinTextures;
import net.minecraft.util.AssetInfo;
import net.minecraft.util.Identifier;

@Mixin(PlayerListEntry.class)
public class PlayerListEntryMixin {
	@Shadow @Final private GameProfile profile;
	
	@Inject(method = "getSkinTextures", at = @At("TAIL"), cancellable = true)
    private void onGetSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
        if (CustomCapes.INSTANCE.isToggled()) {
            SkinTextures oldTextures = cir.getReturnValue();
            if (!profile.name().equalsIgnoreCase(MinecraftClient.getInstance().player.getName().getString())) {
            	return;
            }
            Identifier capeTexture = Identifier.of("nightclient", "capes/gay.png");
            SkinTextures newTextures = new SkinTextures(
                oldTextures.body(),
                new AssetInfo.SkinAssetInfo(capeTexture, "nightclientcape"),
                oldTextures.elytra(),
                oldTextures.model(),
                oldTextures.secure()
            );
            cir.setReturnValue(newTextures);
        }
    }
}