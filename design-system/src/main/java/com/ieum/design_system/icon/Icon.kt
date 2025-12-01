package com.ieum.design_system.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ieum.design_system.R
import com.ieum.design_system.theme.White

@Composable
fun IEUMIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        modifier = modifier.size(
            width = 50.dp,
            height = 26.dp,
        ),
        painter = painterResource(R.drawable.ic_ieum),
        tint = Color.Unspecified,
        contentDescription = "ic-logo",
    )
}

@Composable
fun BackIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "ic-back",
    )
}

@Composable
fun InfoCircleIcon() {
    Icon(
        modifier = Modifier.size(20.dp),
        painter = painterResource(R.drawable.ic_info_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-info-circle",
    )
}

@Composable
fun CheckCircleIcon() {
    Icon(
        modifier = Modifier.size(88.dp),
        painter = painterResource(R.drawable.ic_check_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-check-circle",
    )
}

@Composable
fun CloseIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_close),
        tint = Color.Unspecified,
        contentDescription = "ic-close",
    )
}

@Composable
fun CloseCircleIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_close_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-close-circle",
    )
}

@Composable
fun WellnessIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_wellness),
        tint = Color.Unspecified,
        contentDescription = "ic-wellness",
    )
}

@Composable
fun DailyIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_daily),
        tint = Color.Unspecified,
        contentDescription = "ic-daily",
    )
}

@Composable
fun ThunderIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_thunder),
        tint = Color.Unspecified,
        contentDescription = "ic-thunder",
    )
}

@Composable
fun MedicineIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_medicine),
        tint = Color.Unspecified,
        contentDescription = "ic-medicine",
    )
}

@Composable
fun MealIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_meal),
        tint = Color.Unspecified,
        contentDescription = "ic-meal",
    )
}

@Composable
fun MemoIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_memo),
        tint = Color.Unspecified,
        contentDescription = "ic-memo",
    )
}

@Composable
fun ImageIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_image),
        tint = Color.Unspecified,
        contentDescription = "ic-image",
    )
}

@Composable
fun CommunityIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_community),
        tint = Color.Unspecified,
        contentDescription = "ic-community",
    )
}

@Composable
fun EatWellIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_eat_well),
        tint = Color.Unspecified,
        contentDescription = "ic-eat-well",
    )
}

@Composable
fun EatSmallAmountsIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_eat_small_amounts),
        tint = Color.Unspecified,
        contentDescription = "ic-eat-small-amounts",
    )
}

@Composable
fun EatBarelyIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_eat_barely),
        tint = Color.Unspecified,
        contentDescription = "ic-eat-barely",
    )
}

@Composable
fun CompleteIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_complete),
        tint = Color.Unspecified,
        contentDescription = "ic-complete",
    )
}

@Composable
fun IncompleteIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_incomplete),
        tint = Color.Unspecified,
        contentDescription = "ic-incomplete",
    )
}

@Composable
fun PlusCircleIcon(
    enabled: Boolean = true,
) {
    Icon(
        modifier = Modifier
            .size(24.dp)
            .alpha(
                if (enabled) 1f else 0.2f
            ),
        painter = painterResource(R.drawable.ic_plus_circle),
        tint = Color.Unspecified,
        contentDescription = "ic-plus-circle",
    )
}

@Composable
fun MoodHappyIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_mood_happy),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-happy",
    )
}

@Composable
fun MoodGoodIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_mood_good),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-good",
    )
}

@Composable
fun MoodNormalIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_mood_normal),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-normal",
    )
}

@Composable
fun MoodBadIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_mood_bad),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-bad",
    )
}

@Composable
fun MoodWorstIcon(size: Int) {
    Icon(
        modifier = Modifier.size(size.dp),
        painter = painterResource(R.drawable.ic_mood_worst),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-worst",
    )
}

@Composable
fun MoodSelectIcon() {
    Icon(
        modifier = Modifier.size(108.dp),
        painter = painterResource(R.drawable.ic_mood_select),
        tint = Color.Unspecified,
        contentDescription = "ic-mood-select",
    )
}

@Composable
fun RefreshBlackIcon(modifier: Modifier) {
    Icon(
        modifier = modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_refresh_black),
        tint = Color.Unspecified,
        contentDescription = "ic-refresh-black",
    )
}

@Composable
fun LeftIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_left),
        tint = White,
        contentDescription = "ic-left",
    )
}

@Composable
fun RightIcon(
    color: Color = White,
) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_right),
        tint = color,
        contentDescription = "ic-right",
    )
}

@Composable
fun CommentIcon() {
    Icon(
        modifier = Modifier.size(28.dp),
        painter = painterResource(R.drawable.ic_comment),
        contentDescription = "ic-comment",
    )
}

@Composable
fun HeartIcon() {
    Icon(
        modifier = Modifier.size(28.dp),
        painter = painterResource(R.drawable.ic_heart),
        contentDescription = "ic-heart",
    )
}

@Composable
fun MenuIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_menu),
        contentDescription = "ic-menu",
    )
}

@Composable
fun PenIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_pen),
        tint = White,
        contentDescription = "ic-pen",
    )
}

@Composable
fun FeedLightIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_feed_light),
        contentDescription = "ic-feed-light",
    )
}

@Composable
fun FeedDarkIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_feed_dark),
        contentDescription = "ic-feed-dark",
    )
}

@Composable
fun CalendarIcon(tint: Color) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_calendar),
        tint = tint,
        contentDescription = "ic-calendar",
    )
}

@Composable
fun CalendarLightIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_calendar_light),
        contentDescription = "ic-calendar-light",
    )
}

@Composable
fun CalendarDarkIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_calendar_dark),
        contentDescription = "ic-calendar-dark",
    )
}

@Composable
fun ProfileLightIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_profile_light),
        contentDescription = "ic-profile-light",
    )
}

@Composable
fun ProfileDarkIcon() {
    Icon(
        modifier = Modifier.size(32.dp),
        painter = painterResource(R.drawable.ic_profile_dark),
        contentDescription = "ic-profile-dark",
    )
}

@Composable
fun SettingIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_setting),
        tint = Color.Unspecified,
        contentDescription = "ic-setting",
    )
}

@Composable
fun LockIcon() {
    Icon(
        modifier = Modifier.size(16.dp),
        painter = painterResource(R.drawable.ic_lock),
        tint = Color.Unspecified,
        contentDescription = "ic-lock",
    )
}