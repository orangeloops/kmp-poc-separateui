import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct DeleteDurationField: View {
    @StateViewModel var formViewModel = FormViewModel()

    var body: some View {
        VStack{
            Text("Duration")
                .padding(.top, 10)
            
            HStack {
                Button(action: {
                    formViewModel.setSelectedDuration(duration: max(0, formViewModel.selectedDuration - 60)) // Evitar valores negativos
                }) {
                    Image(systemName: "minus")
                        .padding()
                        .foregroundColor(.black)
                }
                .padding(.leading, 40)

                Spacer()
                Text(timeString(from: formViewModel.selectedDuration))
                    .font(.largeTitle)
                    .foregroundColor(.gray)
                    .padding()
                    .background(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(.black, lineWidth: 1)
                    )
                Spacer()
                Button(action: {
                    formViewModel.setSelectedDuration(duration: formViewModel.selectedDuration + 60) // Evitar valores negativos
                }) {
                    Image(systemName: "plus")
                        .padding()
                        .foregroundColor(.black)
                }
                .padding(.trailing, 40)
            }
            .padding(.bottom, 10)
            
            HStack {
                DeleteDurationAdjustButton(label: "-0:30", adjustment: -30 * 60)
                DeleteDurationAdjustButton(label: "-1:00", adjustment: -60 * 60)
                AddDurationAdjustButton(label: "+0:30", adjustment: 30 * 60)
                AddDurationAdjustButton(label: "+1:00", adjustment: 60 * 60)
            }
            .padding()
        }
    }

    private func timeString(from interval: TimeInterval) -> String {
        let hours = Int(interval) / 3600
        let minutes = (Int(interval) % 3600) / 60
        return String(format: "%02d:%02d", hours, minutes)
    }
}

