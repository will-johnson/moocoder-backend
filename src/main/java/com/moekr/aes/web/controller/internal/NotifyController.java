package com.moekr.aes.web.controller.internal;

import com.moekr.aes.logic.service.NotifyService;
import com.moekr.aes.util.AesProperties;
import com.moekr.aes.util.exceptions.AccessDeniedException;
import com.moekr.aes.util.exceptions.ServiceException;
import com.moekr.aes.web.dto.webhook.WebHookDTO;
import com.moekr.aes.web.response.EmptyResponse;
import com.moekr.aes.web.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/internal/notify", headers = "X-Moocoder-Secret")
public class NotifyController {
	private final String secret;
	private final NotifyService notifyService;

	@Autowired
	public NotifyController(AesProperties properties, NotifyService notifyService) {
		this.secret = properties.getSecret();
		this.notifyService = notifyService;
	}

	@PostMapping("/webhook/{id:\\d+}")
	public Response webHook(@PathVariable int id,
							@RequestHeader("X-Moocoder-Secret") String secret,
							@RequestBody WebHookDTO webHookDTO) throws ServiceException {
		if (!this.secret.equals(secret)) {
			throw new AccessDeniedException();
		}
		notifyService.webHook(id, webHookDTO.getCheckoutSha());
		return new EmptyResponse();
	}

	@PostMapping("/callback/{id:\\d+}/{buildNumber:\\d+}")
	public Response callback(@PathVariable int id,
							 @PathVariable int buildNumber,
							 @RequestHeader("X-Moocoder-Secret") String secret) throws ServiceException {
		if (!this.secret.equals(secret)) {
			throw new AccessDeniedException();
		}
		notifyService.callback(id, buildNumber);
		return new EmptyResponse();
	}
}
