package io.booogyboooo.nightclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.booogyboooo.nightclient.NightClientRecode;
import io.booogyboooo.nightclient.event.EventData;
import io.booogyboooo.nightclient.event.EventType;
import io.booogyboooo.nightclient.util.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
	
    private boolean wasAttackKeyPressed = false;
	
    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void outlineEntities(Entity entity, CallbackInfoReturnable<Boolean> ci) {
    	if (RenderUtil.getEntitys().contains(entity.getType())) {
    		ci.setReturnValue(true);
    	}
    }
    
    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    private void onHandleInputEvents(CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.attackKey.isPressed() && !wasAttackKeyPressed) {
        	EventData data = new EventData(true, EventType.LEFT_CLICK);
        	try {
    			NightClientRecode.getEventManager().fireEvent(EventType.LEFT_CLICK, data);
    		} catch (Exception e) {

    		}
        	if (data.isCanceled) {
        		info.cancel();
        		wasAttackKeyPressed = false;
        	}
        } else if (!client.options.attackKey.isPressed()) {
            wasAttackKeyPressed = false;
        }
    }

}