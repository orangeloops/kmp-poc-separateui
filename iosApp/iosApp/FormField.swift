import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct FormField: View {
    var label: String
    @Environment(\.colorScheme) var colorScheme
    var value: String
    @StateViewModel var formViewModel = FormViewModel()

    var body: some View {
        HStack {
            Text(label)
            Spacer()
            if (value == "client"){
                Text(formViewModel.selectedClient)
                    .foregroundColor(.gray)
            } else if (value == "project") {
                Text(formViewModel.selectedProject)
                    .foregroundColor(.gray)
            } else {
                Text(formViewModel.selectedTask)
                    .foregroundColor(.gray)
            }
            
        }
        .padding()
        .cornerRadius(10)
        .foregroundColor(colorScheme == .light ? .black : .white)
    }
}
