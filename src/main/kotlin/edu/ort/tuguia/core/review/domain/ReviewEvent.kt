package edu.ort.tuguia.core.review.domain

import org.springframework.context.ApplicationEvent

class ReviewEvent(
    source: Any,
    val review: Review,
) : ApplicationEvent(source)