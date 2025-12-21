@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    @Autowired
    private RewardRuleRepository rewardRuleRepository;

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {

        RewardRule existing = rewardRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        existing.setRuleName(updated.getRuleName());
        existing.setRewardPoints(updated.getRewardPoints());
        existing.setCategory(updated.getCategory());
        existing.setMerchant(updated.getMerchant());
        existing.setBonusPoints(updated.getBonusPoints());
        existing.setActive(updated.isActive());

        return rewardRuleRepository.save(existing);
    }
}
