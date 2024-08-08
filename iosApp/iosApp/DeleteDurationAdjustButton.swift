import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct DeleteDurationAdjustButton: View {
    @StateViewModel var formViewModel = FormViewModel()
    var label: String
    var adjustment: TimeInterval

    var body: some View {
        Button(action: {
            if !(adjustment < 0 && -adjustment > formViewModel.selectedDuration){
                formViewModel.setSelectedDuration(duration: formViewModel.selectedDuration + adjustment)
                //duration += adjustment
            }
        }) {
            Text(label)
                .foregroundColor(Color(red: 0.8, green: 0.0, blue: 0.0))
                .font(.footnote)
                .fontWeight(.medium)
                .padding(5) // Añadir padding para que el rectángulo no esté pegado al texto
                .background(
                    RoundedRectangle(cornerRadius: 5)
                        .stroke(Color(UIColor.lightGray), lineWidth: 1)
                )
        }
        .buttonStyle(.borderless)
    }
}

