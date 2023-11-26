package com.example.munchstr.model

data class FollowersAndFollowing(
    val followers:List<UserForProfile>,
    val following:List<UserForProfile>
)