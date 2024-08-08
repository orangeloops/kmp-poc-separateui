import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct AddDurationAdjustButton: View {
    @StateViewModel var formViewModel = FormViewModel()
    var label: String
    var adjustment: TimeInterval
    //@Binding var duration: TimeInterval

    var body: some View {
        Button(action: {
            formViewModel.setSelectedDuration(duration: formViewModel.selectedDuration + adjustment)
            //duration = formViewModel.selectedDuration + adjustment
        }) {
            Text(label)
                .foregroundColor(Color(red: 0.0, green: 0.5, blue: 0.0))
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
