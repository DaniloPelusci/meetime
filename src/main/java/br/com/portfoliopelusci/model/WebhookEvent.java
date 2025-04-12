package br.com.portfoliopelusci.model;

public class WebhookEvent {
	
	private Long eventId;
    private Long subscriptionId;
    private Long portalId;
    private Long appId;
    private Long occurredAt;
    private String subscriptionType;
    private int attemptNumber;
    private Long objectId;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Long getPortalId() {
		return portalId;
	}
	public void setPortalId(Long portalId) {
		this.portalId = portalId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getOccurredAt() {
		return occurredAt;
	}
	public void setOccurredAt(Long occurredAt) {
		this.occurredAt = occurredAt;
	}
	public String getSubscriptionType() {
		return subscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}
	public int getAttemptNumber() {
		return attemptNumber;
	}
	public void setAttemptNumber(int attemptNumber) {
		this.attemptNumber = attemptNumber;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

    

}
