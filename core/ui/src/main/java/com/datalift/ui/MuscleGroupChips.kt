package com.datalift.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.datalift.designsystem.icon.DataliftIcons
import com.datalift.designsystem.theme.DataliftTheme
import com.datalift.model.data.MuscleGroup


fun LazyListScope.muscleGroupChips(
    selectMuscleGroup: (MuscleGroup, Boolean) -> Unit,
){
    items(MuscleGroup.entries){ muscleGroup ->
        MuscleGroupChip(
            muscleGroup = muscleGroup,
            selectMuscleGroup = selectMuscleGroup,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun MuscleGroupChip(
    muscleGroup: MuscleGroup,
    selectMuscleGroup: (MuscleGroup, Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        selected = selected,
        onClick = {
            selected = !selected
            selectMuscleGroup(muscleGroup, selected)
        },
        label = { Text(muscleGroup.displayName) },
        trailingIcon = if(selected) {
            {
                Icon(
                    imageVector = DataliftIcons.Checked,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun MuscleGroupChipPreview(){
    DataliftTheme {
        LazyRow {
            muscleGroupChips(
                selectMuscleGroup = { _, _ -> },
            )
        }
    }
}